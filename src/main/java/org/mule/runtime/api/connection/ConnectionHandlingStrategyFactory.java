/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import org.mule.runtime.api.config.PoolingProfile;

/**
 * Factory to create instances of {@link ConnectionHandlingStrategy}
 *
 * @param <Connection> the generic type of the connections that will be produced
 * @since 1.0
 */
public interface ConnectionHandlingStrategyFactory<Connection>
{

    /**
     * Creates a strategy which supports pooling but does not enforce it. It is
     * possible to disable pooling through a {@link PoolingProfile} which
     * {@link PoolingProfile#isDisabled()} method returns {@code true}.
     * <p/>
     * If pooling is enabled, then invoking {@link ConnectionHandler#release()} will not close
     * the connection, it will just return it to the pool. If disabled, then the release method
     * will actually close the connection.
     *
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> supportsPooling();

    /**
     * Performs the exact same contract as {@link #supportsPooling()} but adding the possibility
     * to specify a {@link PoolingListener} which allows additional custom handling of the pooled {@code Connections}
     * when they're borrowed and returned to the pool
     *
     * @param poolingListener a not {@code null }{@link PoolingListener}
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> supportsPooling(PoolingListener<Connection> poolingListener);

    /**
     * Creates a strategy in which pooling is enforced. {@link PoolingProfile}s which
     * {@link PoolingProfile#isDisabled()} method returns {@code true} are not accepted
     * and will generate an {@link IllegalArgumentException}.
     * <p/>
     * Invoking {@link ConnectionHandler#release()} will not close the connection, it will just
     * return it to the pool.
     *
     * @return a {@link ConnectionHandlingStrategy}
     * @throws IllegalArgumentException if {@code defaultPoolingProfile} attempts to disable pooling
     */
    ConnectionHandlingStrategy<Connection> requiresPooling();

    /**
     * Performs the exact same contract as {@link #requiresPooling()} but adding the possibility
     * to specify a {@link PoolingListener} which allows additional custom handling of the pooled {@code Connections}
     * when they're borrowed and returned to the pool
     *
     * @param poolingListener a not {@code null }{@link PoolingListener}
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> requiresPooling(PoolingListener<Connection> poolingListener);

    /**
     * Returns a strategy which lazily creates and caches connections so that each invocation to the
     * {@link ConnectionProvider#connect()} method using the same config argument results in the same
     * connection. Invoking {@link ConnectionHandler#release()} will not close the connection.
     *
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> cached();

    /**
     * Creates a strategy which adds no behaviour. A new connection is created each time
     * {@link ConnectionHandlingStrategy#getConnectionHandler()} is invoked and will be closed when
     * {@link ConnectionHandler#release()} is called.
     *
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> none();
}
