/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import static java.lang.Math.min;
import static java.lang.Math.toIntExact;
import static java.lang.String.format;
import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteBuffer.wrap;
import static java.nio.channels.Channels.newChannel;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.internal.stream.bytes.TempFileHelper.createBufferFile;
import static org.mule.runtime.internal.stream.bytes.TempFileHelper.deleteAsync;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.util.func.UnsafeRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A {@link TraversableBuffer} which is capable of handling datasets larger than this buffer's size.
 * <p>
 * This buffer works by keeping an in-memory buffer which holds as many information as possible. When
 * information which is ahead of the buffer's current position is requested then the following happens:
 * <p>
 * <ul>
 * <li>The contents of the buffer are wriiten into a temporal file</li>
 * <li>The buffer is cleared</li>
 * <li>The stream is consumed until the buffer is full again or the stream reaches its end</li>
 * <li>If the required data is still ahead of the buffer, then the process is repeated until the data is reached or the stream
 * fully consumed</li>
 * </ul>
 * <p>
 * Another possible scenario, is one in which the data requested is behind the buffer's current position, in which case
 * the data is obtained by reading the temporal file.
 * <p>
 * In either case, what's really important to understand is that the buffer is <b>ALWAYS</b> moving forward. The buffer
 * will never go back and reload data from the temporal file. It only gets data from the stream.
 *
 * @since 1.0
 */
public final class FileStoreTraversableBuffer extends BaseTraversableBuffer {

  private final InputStream stream;
  private final ByteBuffer buffer;
  private final File bufferFile;
  private final RandomAccessFile fileStore;
  private final ReadableByteChannel streamChannel;
  private final Lock bufferLock = new ReentrantLock();
  private final Lock fileStoreLock = new ReentrantLock();

  private Range bufferRange;
  private boolean streamFullyConsumed = false;

  /**
   * Creates a new instance
   *
   * @param stream     The stream being buffered. This is the original data source
   * @param available  a piece of information which has already been pulled from the stream and is set as the buffer's original state
   * @param bufferSize the buffer size
   */
  public FileStoreTraversableBuffer(InputStream stream, byte[] available, int bufferSize) {
    super(bufferSize);
    this.stream = stream;
    this.buffer = allocateDirect(bufferSize);

    bufferFile = createBufferFile("stream-buffer");
    try {
      fileStore = new RandomAccessFile(bufferFile, "rw");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(format("Buffer file %s was just created but now it doesn't exist",
                                        bufferFile.getAbsolutePath()));
    }

    if (available != null && available.length > 0) {
      try {
        fileStore.getChannel().write(wrap(available));
      } catch (IOException e) {
        throw new MuleRuntimeException(createStaticMessage("Could not buffer prefetched contents to disk"), e);
      }

      buffer.put(available);
      buffer.flip();
      bufferRange = new Range(0, available.length);
    } else {
      bufferRange = new Range(0, 0);
    }

    streamChannel = newChannel(stream);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected int doGet(ByteBuffer destination, long position, int length) {
    return get(destination, position, length, true);
  }

  private int get(ByteBuffer dest, long position, int length, boolean consumeStreamIfNecessary) {
    Range requiredRange = new Range(position, position + length);

    bufferLock.lock();

    try {

      if (streamFullyConsumed && requiredRange.startsAfter(bufferRange)) {
        return -1;
      }

      if (bufferRange.contains(requiredRange)) {
        ByteBuffer src = buffer.duplicate();
        final int newPosition = toIntExact(requiredRange.start - bufferRange.start);
        src.position(newPosition);
        src.limit(newPosition + min(length, bufferSize - newPosition));
        dest.put(src);
        return length;
      }

      if (bufferRange.isAhead(requiredRange) || streamFullyConsumed) {
        bufferLock.unlock(); // we don't need to hold this lock anymore
        return getDataFromStore(dest, requiredRange, length);
      }

      if (consumeStreamIfNecessary) {
        while (!streamFullyConsumed && bufferRange.isBehind(requiredRange)) {
          try {
            reloadBuffer();
          } catch (IOException e) {
            throw new MuleRuntimeException(createStaticMessage("Could not read stream"), e);
          }
        }

        return get(dest, position, length, false);
      } else {
        return -1;
      }
    } finally {
      try {
        bufferLock.unlock();
      } catch (IllegalMonitorStateException e) {
        // lock was released early to improve performance and somebody else took it. This is fine
      }
    }
  }

  private int getDataFromStore(ByteBuffer dest, Range requiredRange, int length) {
    try {
      ByteBuffer buffer = allocateDirect(length);
      return withFileLock(() -> {
        int read = fileStore.getChannel().read(buffer, requiredRange.start);
        if (read > 0) {
          buffer.flip();
          dest.put(buffer);
        }
        return read;
      });
    } catch (IOException e) {
      throw new MuleRuntimeException(createStaticMessage("Could not read from file store"), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doClose() {
    buffer.clear();
    safely(streamChannel::close);
    safely(fileStore::close);
    safely(stream::close);
    deleteAsync(bufferFile);
  }

  private <T> T withFileLock(Callable<T> callable) throws IOException {
    fileStoreLock.lock();
    try {
      return callable.call();
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new MuleRuntimeException(e);
    } finally {
      fileStoreLock.unlock();
    }
  }

  private int reloadBuffer() throws IOException {
    if (streamFullyConsumed) {
      return -1;
    }

    int result;
    buffer.clear();
    result = withFileLock(() -> fileStore.getChannel().read(buffer));
    if (result < 0) {
      result = streamChannel.read(buffer);
      if (result >= 0) {
        //Put the cursor at the end
        buffer.flip();
        withFileLock(() -> fileStore.getChannel().write(buffer));
        bufferRange = bufferRange.advance(result);
      } else {
        streamFullyConsumed = true;
      }
    }

    buffer.flip();
    return result;
  }

  private void safely(UnsafeRunnable task) {
    try {
      task.run();
    } catch (Exception e) {
      // log
    }
  }


  private class Range {

    private final long start;
    private final long end;

    private Range(long start, long end) {
      checkArgument(end >= start, "end has to be greater than start");
      this.start = start;
      this.end = end;
    }

    private Range advance(int offset) {
      return new Range(end, end + offset);
    }

    private boolean contains(Range range) {
      return start <= range.start && end >= range.end;
    }

    private boolean isAhead(Range range) {
      return start > range.start && end >= range.end;
    }

    private boolean isBehind(Range range) {
      return end < range.end;
    }

    private boolean startsAfter(Range range) {
      return start > range.end;
    }
  }
}
