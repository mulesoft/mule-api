/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import java.nio.ByteBuffer;

/**
 * Base class for implementations of {@link TraversableBuffer}
 *
 * @since 1.0
 */
public abstract class BaseTraversableBuffer implements TraversableBuffer {

  protected final int bufferSize;
  private boolean closed = false;

  /**
   * Creates a new instance
   *
   * @param bufferSize the buffer size
   */
  public BaseTraversableBuffer(int bufferSize) {
    this.bufferSize = bufferSize;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalStateException if the buffer is closed
   */
  @Override
  public final int get(ByteBuffer destination, long position, int length) {
    if (closed) {
      throw new IllegalStateException("Buffer is closed");
    }

    return doGet(destination, position, length);
  }

  /**
   * Template method for getting information out of the buffer
   *
   * @param destination the buffer in which information is to be written
   * @param position    the position from which to start pulling information
   * @param length      the amount of bytes to copy
   * @return the amount of bytes read of {@code -1} if none could be read.
   */
  protected abstract int doGet(ByteBuffer destination, long position, int length);

  /**
   * Closes the buffer, releasing held resources
   */
  public final void close() {
    closed = true;
    doClose();
  }

  /**
   * Template method for actually closing the buffer
   */
  protected abstract void doClose();
}
