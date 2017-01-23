/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming;

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link InputStream} which provides random access.
 * <p>
 * It works by adding the concept of a zero-base position. Each position
 * represents one byte in the stream. Each time data is pulled from this stream,
 * the position advances as many bytes as read. However, the {@link #seek(long)}
 * method can be used to reset the position.
 * <p>
 * This is not thread safe. Should not be used concurrently
 *
 * @since 1.0
 */
public abstract class CursorStream extends InputStream {

  /**
   * @return The cursor's current position
   */
  public abstract long getPosition();

  /**
   * Updates the cursor's position.
   *
   * @param position the new position
   */
  public abstract void seek(long position) throws IOException;

  /**
   * @return Whether this stream is closed or not.
   */
  public abstract boolean isClosed();
}
