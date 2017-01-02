/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.stream.bytes;

import static java.util.Arrays.copyOfRange;
import static java.util.Collections.newSetFromMap;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import static org.mule.runtime.api.util.Preconditions.checkState;
import static org.slf4j.LoggerFactory.getLogger;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.internal.stream.bytes.BaseTraversableBuffer;
import org.mule.runtime.internal.stream.bytes.ByteArrayTraversableBuffer;
import org.mule.runtime.internal.stream.bytes.BufferedCursorStream;
import org.mule.runtime.internal.stream.bytes.FileStoreTraversableBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.slf4j.Logger;

/**
 * An {@link InputStream} decorator which supports concurrent random access.
 * <p>
 * This class works by buffering an existing {@link InputStream}. This buffering allows for data
 * to be kept so that random access is allowed. The size of the buffer is configured through the
 * constructor. Upon creation, the wrapped stream will be consumed until the buffer is full.
 * If the wrapped stream is fully consumed before the buffer is full, then those contents will be
 * kept in memory for fast random access. If the buffer gets full before the stream is fully consumed,
 * then a temporal file will be used to buffer the contents.
 * <p>
 * Regardless of the buffering strategy, safe concurrent access will be guaranteed.
 * <p>
 * Although this decorator is perfectly functional as a stream, the {@link #openCursor()} method
 * should be used in order to support random concurrent access. That method creates a new
 * {@link CursorStream} which is linked to this instance and leverages the buffer. It allows different clients
 * to reference different positions on the buffer and access them in a thread safe manner.
 * <p>
 * This stream is not closed until all open cursors are closed. This makes it critical for anyone invoking the
 * {@link #openCursor()} method to make sure that the cursor is closed when no longer needed, since otherwise
 * any resources held by the underlying buffer will never be released.
 *
 * @see CursorStream
 * @since 1.0
 */
public class TraversableStream extends InputStream {

  private static final Logger LOGGER = getLogger(TraversableStream.class);

  private final InputStream wrappedStream;
  private final int bufferSize;
  private final Set<CursorStream> cursors = newSetFromMap(new ConcurrentHashMap<>());

  private boolean closed = false;
  private CursorStream defaultCursor;
  private Supplier<CursorStream> defaultCursorSupplier = this::computeDefaultCursor;
  private Supplier<BaseTraversableBuffer> bufferSupplier = this::computeBuffer;
  private BaseTraversableBuffer buffer;


  /**
   * Creates a new instance
   *
   * @param wrappedStream the original stream to be decorated
   * @param bufferSize    the size in bytes of the buffer
   */
  public TraversableStream(InputStream wrappedStream, int bufferSize) {
    this.wrappedStream = wrappedStream;
    this.bufferSize = bufferSize;
  }

  /**
   * Creates a new instance
   *
   * @param bytes      a byte array to be exposed through this class
   * @param bufferSize the size in bytes of the buffer
   */
  public TraversableStream(byte[] bytes, int bufferSize) {
    this(new ByteArrayInputStream(bytes), bufferSize);
  }

  /**
   * Creates a new {@link CursorStream} positioned on the very beginning of the wrapped stream.
   * <p>
   * Notices that this method enables concurrent random access by providing the ability to open
   * several cursors. However, each cursor should not be used concurrently since {@link CursorStream}
   * does not guarantee thread safeness.
   * <p>
   * It is the invokers responsibility to make sure that the returned cursor gets closed, since
   * otherwise whatever resources held by the underlying buffer will never be released.
   *
   * @return a new {@link CursorStream}
   * @throws IllegalStateException if invoked after the {@link #close()} method has been invoked
   */
  public CursorStream openCursor() {
    checkState(!closed, "Cannot open a new cursor on a closed stream");
    CursorStream cursor = new BufferedCursorStream(bufferSupplier.get(), this::onCursorClose, bufferSize);
    cursors.add(cursor);

    return cursor;
  }

  private void onCursorClose(CursorStream cursor) {
    cursors.remove(cursor);
    if (allCursorsClosed()) {
      buffer.close();
    }
  }

  private boolean allCursorsClosed() {
    return defaultCursorSupplier.get().isClosed() && cursors.isEmpty();
  }

  private BaseTraversableBuffer computeBuffer() {
    synchronized (this) {
      if (buffer == null) {
        byte[] array = readUntil(wrappedStream, bufferSize);
        if (array.length < bufferSize) {
          // It fits into memory
          try {
            buffer = new ByteArrayTraversableBuffer(array);
          } finally {
            try {
              wrappedStream.close();
            } catch (IOException e) {
              if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Could not properly close stream", e);
              }
            }
          }
        } else {
          buffer = new FileStoreTraversableBuffer(wrappedStream, array, bufferSize);
        }
        bufferSupplier = () -> buffer;
      }

      return buffer;
    }
  }

  private CursorStream computeDefaultCursor() {
    synchronized (this) {
      if (defaultCursor == null) {
        defaultCursor = openCursor();
        defaultCursorSupplier = () -> defaultCursor;
      }

      return defaultCursor;
    }
  }

  private byte[] readUntil(InputStream in, int max) {
    byte[] array = new byte[max];
    int arrayPos = 0;
    try {
      in.available();
      int readCount = in.read(array, arrayPos, array.length - arrayPos);
      do {
        arrayPos += readCount;
        readCount = in.read(array, arrayPos, array.length - arrayPos);
      } while (readCount > 0);
    } catch (IOException e) {
      throw new MuleRuntimeException(createStaticMessage("Could not read from stream"), e);
    }

    return copyOfRange(array, 0, arrayPos);
  }

  /**
   * Closes this stream in the sense that it can no longer be consumed directly and that no new
   * cursors will be opened. However, all opened cursors will still be functional and resources
   * held by this instance will not be released until all cursors are closed.
   */
  @Override
  public void close() throws IOException {
    defaultCursorSupplier.get().close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mark(int readlimit) {
    defaultCursorSupplier.get().mark(readlimit);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() throws IOException {
    defaultCursorSupplier.get().reset();
  }

  /**
   * {@inheritDoc}
   *
   * @return {@code true}
   */
  @Override
  public boolean markSupported() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {
    return defaultCursorSupplier.get().read();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b) throws IOException {
    return defaultCursorSupplier.get().read(b);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return defaultCursorSupplier.get().read(b, off, len);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long n) throws IOException {
    return defaultCursorSupplier.get().skip(n);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {
    return defaultCursorSupplier.get().available();
  }
}
