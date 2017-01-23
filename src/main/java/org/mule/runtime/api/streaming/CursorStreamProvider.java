/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming;

import static java.util.Collections.newSetFromMap;
import static org.mule.runtime.api.util.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
public abstract class CursorStreamProvider {

  protected final InputStream wrappedStream;
  protected final int bufferSize;
  private final Set<CursorStream> cursors = newSetFromMap(new ConcurrentHashMap<>());

  private AtomicBoolean closed = new AtomicBoolean(false);

  /**
   * Creates a new instance
   *
   * @param wrappedStream the original stream to be decorated
   * @param bufferSize    the size in bytes of the buffer
   */
  public CursorStreamProvider(InputStream wrappedStream, int bufferSize) {
    this.wrappedStream = wrappedStream;
    this.bufferSize = bufferSize;
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
  public final CursorStream openCursor() {
    checkState(!closed.get(), "Cannot open a new cursor on a closed stream");
    CursorStream cursor = doOpenCursor();
    cursors.add(cursor);

    return new ManagedCursorStreamDecorator(cursor);
  }

  public void close() {
    closed.set(true);
  }

  protected abstract void releaseResources();

  protected abstract CursorStream doOpenCursor();

  private void closeCursor(CursorStream cursor) {
    cursors.remove(cursor);
    if (closed.get() && allCursorsClosed()) {
      releaseResources();
    }
  }

  private boolean allCursorsClosed() {
    return cursors.isEmpty();
  }

  private class ManagedCursorStreamDecorator extends CursorStream {

    private final CursorStream delegate;

    private ManagedCursorStreamDecorator(CursorStream delegate) {
      this.delegate = delegate;
    }

    @Override
    public void close() throws IOException {
      try {
        delegate.close();
      } finally {
        closeCursor(delegate);
      }
    }

    @Override
    public long getPosition() {
      return delegate.getPosition();
    }

    @Override
    public void seek(long position) throws IOException {
      delegate.seek(position);
    }

    @Override
    public boolean isClosed() {
      return delegate.isClosed();
    }

    @Override
    public int read() throws IOException {
      return delegate.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
      return delegate.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      return delegate.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
      return delegate.skip(n);
    }

    @Override
    public int available() throws IOException {
      return delegate.available();
    }

    @Override
    public void mark(int readlimit) {
      delegate.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
      delegate.reset();
    }

    @Override
    public boolean markSupported() {
      return delegate.markSupported();
    }
  }
}
