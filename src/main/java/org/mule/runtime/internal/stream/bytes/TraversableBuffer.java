/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.stream.bytes;

import java.nio.ByteBuffer;

/**
 * A buffer which provides concurrent random access to the entirety
 * of a dataset.
 * <p>
 * It works with the concept of a zero-base position. Each position
 * represents one byte in the stream. Although this buffer tracks the
 * position of each byte, it doesn't have a position itself. That means
 * that pulling data from this buffer does not make any current position
 * to be moved.
 *
 * @since 1.0
 */
public interface TraversableBuffer {

  /**
   * Loads data into a provided {@code destination}
   *
   * @param destination the array in which information is to be written
   * @param position    the position from which to start pulling information
   * @param length      the amount of bytes to copy
   * @return the amount of bytes read of {@code -1} if none could be read.
   */
  int get(ByteBuffer destination, long position, int length);
}
