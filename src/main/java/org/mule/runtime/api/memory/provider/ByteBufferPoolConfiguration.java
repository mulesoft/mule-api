/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.memory.provider;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.memory.provider.type.ByteBufferPoolStrategy;

/**
 * A list of properties that are configurable for a byte buffer pool.
 *
 * @since 4.5.0
 */
@Experimental
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

  /**
   * @return the max buffer size for the pool
   */
  int getMaxBufferSize();

  /**
   * @return the pooling strategy to use.
   */
  ByteBufferPoolStrategy getByteBufferPoolStrategy();
}
