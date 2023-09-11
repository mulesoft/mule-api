/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import java.util.concurrent.TimeUnit;

/**
 * Pooling configuration for JDBC Data Sources capable of pooling connections.
 *
 * Unlike {@link PoolingProfile} which serves a more general purpose, this interface should be used when configuring pooling for
 * JDBC connections
 *
 * @since 1.0
 */
// TODO: MULE-9047 - review if we can use the standard pooling profile
public interface DatabasePoolingProfile {

  /**
   * @return Maximum number of connections a pool maintains at any given time
   */
  int getMaxPoolSize();

  /**
   * @return Minimum number of connections a pool maintains at any given time
   */
  int getMinPoolSize();

  /**
   * @return How many connections to acquire at a time when pool is exhausted
   */
  int getAcquireIncrement();

  /**
   * @return How many statements are cached per pooled connection. Defaults to 0, meaning statement caching is disabled
   */
  int getPreparedStatementCacheSize();

  /**
   * @return The number of milliseconds a client trying to obtain a connection waits for it to be checked-in or acquired when the
   *         pool is exhausted. Zero (default) means wait indefinitely
   */
  int getMaxWait();

  /**
   * @return A {@link TimeUnit} which qualifies the {@link #getMaxWait()}.
   */
  TimeUnit getMaxWaitUnit();
}
