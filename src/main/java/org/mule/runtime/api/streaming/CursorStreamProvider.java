/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.streaming;

import java.io.InputStream;

/**
 * Provides instances of {@link CursorStream} which allows concurrent access of a wrapped
 * {@link InputStream}.
 * <p>
 * The provider maintains all the allocated resources necessary to power all the open
 * cursors.
 * <p>
 * When this provider is {@link #close() closed}, it will not open any new cursors
 * and invokations to {@link #openCursor()} will fail. However, the allocated resources
 * will not be released until all open cursors are closed. The runtime will be in charge of
 * doing that, no intervention is required from the client.
 *
 * @see CursorStream
 * @since 1.0
 */
public interface CursorStreamProvider {


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
  CursorStream openCursor();

  /**
   * Closes the provider in the sense that it will not yield any more cursors. However, all active
   * cursors will remain functional. When the last of them is closed, then any resources associated to {@code this}
   * provider will be released.
   */
  void close();

  /**
   * @return Whether the {@link #close()} method has been invoked on {@code this} instance or not
   */
  boolean isClosed();

}
