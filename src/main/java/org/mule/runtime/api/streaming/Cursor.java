/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.streaming;

import org.mule.api.annotation.NoImplement;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provides random access to a stream of data. By stream, you should not assume an {@link InputStream} (although such
 * implementation exists), but instead consider a conceptual stream, regardless of its nature.
 * <p>
 * It works by adding the concept of a zero-based position. Each position represents one byte in the stream. Each time data is
 * pulled from this stream, the position advances as many bytes as read. However, the {@link #seek(long)} method can be used to
 * reset the position.
 * <p>
 * Implementations should not be expected to be thread safe. Should not be used concurrently.
 *
 * @since 1.0
 */
@NoImplement
public interface Cursor extends Closeable {

  /**
   * @return The cursor's current position
   */
  long getPosition();

  /**
   * Updates the cursor's position.
   *
   * @param position the new position
   */
  void seek(long position) throws IOException;

  /**
   * Releases all the resources that {@code this} cursor allocated.
   * <p>
   * Implementation of this method must be idempotent and thread-safe.
   * <p>
   * This method should <b>only</b> be called by the Runtime. Once that happens, the {@link #isReleased()} method will start to
   * return {@code true}
   */
  void release();

  /**
   * @return Whether the {@link #release()} method has been called on {@code this} instance
   */
  boolean isReleased();

  /**
   * @return The {@link CursorProvider} which generated {@code this} cursor
   */
  CursorProvider getProvider();

  /**
   * For a Cursor, to be closed means that this cursor should not provide any more data. It does not mean that allocated resources
   * will be released, that's what the {@link #release()} method is for.
   *
   * Depending on the concrete cursor type, closed cursors might be re-opened or not.
   */
  @Override
  void close() throws IOException;
}
