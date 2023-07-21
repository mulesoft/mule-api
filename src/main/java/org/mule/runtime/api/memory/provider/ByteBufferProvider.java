/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.memory.provider;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.lifecycle.Disposable;

import java.nio.ByteBuffer;

/**
 * A Provider responsible for allocating and releasing memory, required during application runtime.
 *
 * @since 4.5.0
 */
@Experimental
public interface ByteBufferProvider<T extends ByteBuffer> extends Disposable {


  /**
   * @param size the required size.
   *
   * @return allocated Buffer of the required size.
   */
  T allocate(int size);

  /**
   * @param size the required size.
   *
   * @return allocated buffer at least of the provided size.
   */
  T allocateAtLeast(int size);

  /**
   * @param newSize   the required size.
   * @param oldBuffer the old buffer.
   *
   * @return reallocate buffer to a required size.
   */
  T reallocate(T oldBuffer, int newSize);

  /**
   * @param buffer the buffer to be released
   */
  void release(T buffer);

  /**
   * @param size the size of the byte array to provide.
   * @return a byte array of the required size.
   */
  byte[] getByteArray(int size);

  /**
   * Disposes the byte buffer provider
   */
  void dispose();
}
