/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

/**
 * Gets notifications for events related to connections on a pool.
 *
 * @param <Config>     the generic type for the config object to which the connection is bound
 * @param <Connection> the generic type for the pooled connection
 * @since 1.0
 */
public interface PoolingListener<Config, Connection>
{

    /**
     * Executes after the {@code connection} was successfully
     * borrowed from the pool but before it's handed over
     * to the requester.
     * <p>
     * This method can alter the state of the {@code connection}
     * as long as it remains on a usable state.
     *
     * @param config     the config object to which the {@code connection} is bound
     * @param connection the pooled connection
     */
    void onBorrow(Config config, Connection connection);

    /**
     * Executes right before the pool accepts the {@code connection} back.
     * <p>
     * If this method throws exception, then the pool will invalidate the
     * {@code connection}. On the other hand, a successful execution of this
     * method does not imply that the pool will successful accept the connection
     * back. The pool is still free to invalidate the connection anyway.
     * <p>
     * This method can alter the state of the {@code connection}
     * as long as it remains on a usable state.
     *
     * @param config     the config object to which the {@code connection} is bound
     * @param connection the pooled connection
     */
    void onReturn(Config config, Connection connection);
}
