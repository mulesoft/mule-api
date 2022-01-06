/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.memory.provider;


import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.memory.provider.type.ByteBufferType;

/**
 * A Configuration for a {@link ByteBufferProvider}
 */
@Experimental
public interface ByteBufferProviderConfiguration {

  /**
   * @return the type of buffer a {@link ByteBufferProvider} returns.
   */
  ByteBufferType getByteBufferType();

}
