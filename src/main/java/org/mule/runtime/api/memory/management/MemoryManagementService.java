/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.memory.management;

import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.api.memory.provider.ByteBufferProvider;
import org.mule.runtime.api.memory.provider.ByteBufferProviderConfiguration;

/**
 * Provides resources for memory management to the runtime.
 */
public interface MemoryManagementService extends Initialisable, Disposable {

  /**
   *
   * @param name          name for the the registration of the {@link ByteBufferProvider}
   * @param configuration a {@link ByteBufferProviderConfiguration}
   * @return a {@link ByteBufferProvider}
   */
  ByteBufferProvider getByteBufferProvider(String name, ByteBufferProviderConfiguration configuration);
}
