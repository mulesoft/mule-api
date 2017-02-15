/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import static java.nio.ByteBuffer.allocateDirect;
import org.mule.runtime.api.stream.bytes.CursorStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

/**
 * A {@link CursorStream} which pulls its data from a {@link TraversableBuffer}.
 * <p>
 * To reduce contention on the {@link TraversableBuffer}, this class also uses a local intermediate
 * memory buffer which size must be configured
 *
 * @see TraversableBuffer
 * @since 1.0
 */
public final class BufferedCursorStream extends CursorStream {

  private final TraversableBuffer traversableBuffer;
  private final Consumer<CursorStream> closeCallback;
  private boolean closed = false;

  private final int localBufferSize;
  private long position = 0;

  /**
   * Intermediate buffer between this cursor and the {@code traversableBuffer}. This reduces contention
   * on the {@code traversableBuffer}
   */
  private final ByteBuffer localBuffer;

  /**
   * Creates a new instance
   *
   * @param traversableBuffer the buffer which provides data
   * @param closeCallback     A callback to be invoked when the {@link #close()} method is called
   * @param localBufferSize   The size of the intermediate buffer
   */
  public BufferedCursorStream(TraversableBuffer traversableBuffer, Consumer<CursorStream> closeCallback, int localBufferSize) {
    this.traversableBuffer = traversableBuffer;
    this.closeCallback = closeCallback;
    this.localBufferSize = localBufferSize;
    localBuffer = allocateDirect(localBufferSize);
    localBuffer.flip();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getPosition() {
    return position;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void seek(long position) throws IOException {
    assertNotClosed();
    this.position = position;
    localBuffer.clear();
    localBuffer.flip();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {
    assertNotClosed();
    if (reloadLocalBufferIfEmpty() > 0) {
      int read = unsigned((int) localBuffer.get());
      position++;
      return read;
    }

    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    assertNotClosed();
    return readInto(b, off, len);
  }

  private int readInto(byte[] b, int off, int len) throws IOException {
    int read = 0;
    while (true) {
      int remaining = reloadLocalBufferIfEmpty();
      if (remaining == -1) {
        return read == 0 ? -1 : read;
      }

      if (len <= remaining) {
        localBuffer.get(b, off, len);
        position += len;
        return read + len;
      } else {
        localBuffer.get(b, off, remaining);
        position += remaining;
        read += remaining;
        off += remaining;
        len -= remaining;
      }
    }
  }

  private int reloadLocalBufferIfEmpty() {
    if (!localBuffer.hasRemaining()) {
      localBuffer.clear();
      int read = traversableBuffer.get(localBuffer, position, localBufferSize);
      if (read > 0) {
        localBuffer.flip();
        return read;
      } else {
        return -1;
      }
    }

    return localBuffer.remaining();
  }

  /**
   * Closes this stream and invokes the closing callback received in the constructor.
   */
  @Override
  public void close() throws IOException {
    if (!closed) {
      closed = true;
      closeCallback.accept(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosed() {
    return closed;
  }

  private int unsigned(int value) {
    return value & 0xff;
  }

  private void assertNotClosed() {
    if (closed) {
      throw new IllegalStateException("Stream is closed");
    }
  }
}
