/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.memory.provider;

/**
 * A list of properties that are configurable for a byte buffer pool.
 */
public interface ByteBufferPoolConfiguration {

  /**
   * @return the starting buffer size to have on creation of each of the pool.
   */
  int getBaseByteBufferSize();

  /**
   * @return the number of pools to be created.
   */
  int getNumberOfPools();

  /**
   * @return the growth factor for the creation of the different pools.
   */
  int getGrowthFactor();

}
