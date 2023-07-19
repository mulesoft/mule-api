/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.memory.provider;


import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.memory.provider.type.ByteBufferType;

/**
 * A Configuration for a {@link ByteBufferProvider}
 *
 * @since 4.5.0
 */
@Experimental
public interface ByteBufferProviderConfiguration {

  /**
   * @return the type of buffer a {@link ByteBufferProvider} returns.
   */
  ByteBufferType getByteBufferType();

}
