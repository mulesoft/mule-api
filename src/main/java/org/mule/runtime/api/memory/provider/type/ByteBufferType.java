/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.memory.provider.type;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.memory.provider.ByteBufferProvider;

/**
 * Represents the type of a {@link java.nio.ByteBuffer} that a {@link ByteBufferProvider} provides.
 *
 * @since 4.5.0
 */
@Experimental
public enum ByteBufferType {

  /**
   * Native off-heap memory.
   */
  DIRECT,

  /**
   * Heap memory.
   */
  HEAP

}
