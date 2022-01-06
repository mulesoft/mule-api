/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.memory.management;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.memory.provider.ByteBufferPoolConfiguration;
import org.mule.runtime.api.memory.provider.ByteBufferProvider;
import org.mule.runtime.api.memory.provider.ByteBufferProviderConfiguration;
import org.mule.runtime.api.memory.provider.type.ByteBufferType;

/**
 * Provides resources for memory management to the runtime.
 *
 * @since 4.5.0
 */
@Experimental
public interface MemoryManagementService extends Initialisable, Disposable {

  /**
   * Get a buffer provider with a name and type.
   *
   * @param name              name for the the registration of the {@link ByteBufferProvider}
   * @param type              the {@link ByteBufferType}
   * @param poolConfiguration the {@link ByteBufferPoolConfiguration}
   * @param maxBufferSize     the max buffer size
   * @return a {@link ByteBufferProvider}
   */
  ByteBufferProvider getByteBufferProvider(String name, ByteBufferType type, int maxBufferSize,
                                           ByteBufferPoolConfiguration poolConfiguration);

  /**
   * Get a buffer provider defined by a name.
   *
   * @param name name for the the registration of the {@link ByteBufferProvider}
   * @param type the {@link ByteBufferType}
   * @return a {@link ByteBufferProvider}
   */
  ByteBufferProvider getByteBufferProvider(String name, ByteBufferType type);

  /**
   * Disposes a byte buffer provider.
   *
   * @param name name of the {@link ByteBufferProvider}
   */
  void disposeByteBufferProvider(String name);
}
