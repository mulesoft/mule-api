/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import static java.lang.Math.toIntExact;

import java.nio.ByteBuffer;

/**
 * A {@link TraversableBuffer} which is backed by a byte array which holds the entity
 * of the dataset in memory.
 * <p>
 * This implementation is only suitable for datasets which fit into memory.
 */
public final class ByteArrayTraversableBuffer extends BaseTraversableBuffer {

  private byte[] bytes;

  /**
   * Creates a new instance
   *
   * @param bytes the data set
   */
  public ByteArrayTraversableBuffer(byte[] bytes) {
    super(bytes.length);
    this.bytes = bytes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int doGet(ByteBuffer destination, long position, int length) {
    if (position >= bytes.length) {
      return -1;
    }

    final long available = bytes.length - position;
    if (available < length) {
      length = toIntExact(available);
    }

    destination.put(bytes, toIntExact(position), length);
    return length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doClose() {
    bytes = null;
  }
}
