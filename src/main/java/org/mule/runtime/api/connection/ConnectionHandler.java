/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import org.mule.api.annotation.NoImplement;

/**
 * Represents a connection that is being managed by the runtime.
 * <p>
 * This is actually a wrapper which provides an unique API for managing connections of different types and using different
 * policies. For example, clients of this API will use the {@link #getConnection()} method to obtain the actual connection that's
 * been managed, without independently of the connection being pooled, cached, lazy, validated, reconnected, etc.
 *
 * @param <T> the generic type of the connection being wrapped by each individual instance
 * @since 1.0
 */
@NoImplement
public interface ConnectionHandler<T> {

  /**
   * Returns the connection that's being wrapped. The actual implications of invoking this method depends on the implementation.
   * <p>
   * No guarantees are offered regarding how this method provisions the connection. The only commitment this method takes is that
   * it will either provide a connection ready to be used or throw a {@link ConnectionException}
   *
   * @return a ready to use connection
   * @throws ConnectionException if a valid connection could not be obtained
   */
  T getConnection() throws ConnectionException;

  /**
   * When a component requests an instance of this interface, it becomes obligated to invoke this method after it no longer
   * requires such instance.
   * <p>
   * Just like with {@link #getConnection()}, the implications of executing this method are not guaranteed. Depending on the
   * implementation, the connection could be closed, returned to a pooled, recycled, etc. It is not to be assumed that any
   * resources allocated by the wrapped connection will be freed after invoking this method.
   * <p>
   * The only strong piece on the contract is the user's obligation to invoke this method once it's done with the instance
   */
  void release();

  /**
   * Similar to {@link #release()} but for cases in which the wrapped connection is in an error state and should not be used
   * anymore.
   * <p>
   * Just like with {@link #release()}, the implications of executing this method are not guaranteed. Depending on the
   * implementation, the effect could be the same as {@link #release()}, pool invalidation, cache eviction, etc.
   * <p>
   * It is not to be assumed that any resources allocated by the wrapped connection will be freed after invoking this method.
   */
  void invalidate();
}
