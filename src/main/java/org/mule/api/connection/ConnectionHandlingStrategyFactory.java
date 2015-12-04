/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.connection;

import org.mule.api.config.PoolingProfile;

/**
 * Factory to create instances of {@link ConnectionHandlingStrategy}
 *
 * @param <Config>     the generic type of the config for which connections will be produced
 * @param <Connection> the generic type of the connections that will be produced
 * @since 1.0
 */
public interface ConnectionHandlingStrategyFactory<Config, Connection>
{

    /**
     * Creates a strategy which supports pooling but does not enforce it. It is
     * possible to disable pooling through a {@link PoolingProfile} which
     * {@link PoolingProfile#isDisabled()} method returns {@code true}.
     * <p>
     * The {@code defaultPoolingProfile} is used to provide a default {@link PoolingProfile},
     * notice however that there's no guarantee that the provided value will actually
     * be used since the runtime might decide to use another instance per user's configuration.
     * <p>
     * If pooling is enabled, then invoking {@link ConnectionHandler#release()} will not close
     * the connection, it will just return it to the pool. If disabled, then the the release method
     * will actually close the connection.
     *
     * @param defaultPoolingProfile the {@link PoolingProfile} to be used by default
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> supportsPooling(PoolingProfile defaultPoolingProfile);

    /**
     * Performs the exact same contract as {@link #supportsPooling(PoolingProfile)} but adding the possibility
     * to specify a {@link PoolingListener} which allows additional custom handling of the pooled {@code Connections}
     * when they're borrowed and returned to the pool
     *
     * @param defaultPoolingProfile the {@link PoolingProfile} to be used by default
     * @param poolingListener       a not {@code null }{@link PoolingListener}
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> supportsPooling(PoolingProfile defaultPoolingProfile, PoolingListener<Config, Connection> poolingListener);

    /**
     * Creates a strategy in which pooling is enforced. {@link PoolingProfile}s which
     * {@link PoolingProfile#isDisabled()} method returns {@code true} are not accepted
     * and will generate an {@link IllegalArgumentException}.
     * <p>
     * The {@code defaultPoolingProfile} is used to provide a default {@link PoolingProfile},
     * notice however that there's no guarantee that the provided value will actually
     * be used since the runtime might decide to use another instance per user's configuration.
     * <p>
     * Invoking {@link ConnectionHandler#release()} will not close the connection, it will just
     * return it to the pool.
     *
     * @param defaultPoolingProfile the {@link PoolingProfile} to be used by default
     * @return a {@link ConnectionHandlingStrategy}
     * @throws IllegalArgumentException if {@code defaultPoolingProfile} attempts to disable pooling
     */
    ConnectionHandlingStrategy<Connection> requiresPooling(PoolingProfile defaultPoolingProfile);

    /**
     * Performs the exact same contract as {@link #requiresPooling(PoolingProfile)} but adding the possibility
     * to specify a {@link PoolingListener} which allows additional custom handling of the pooled {@code Connections}
     * when they're borrowed and returned to the pool
     *
     * @param defaultPoolingProfile the {@link PoolingProfile} to be used by default
     * @param poolingListener       a not {@code null }{@link PoolingListener}
     * @return a {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> requiresPooling(PoolingProfile defaultPoolingProfile, PoolingListener<Config, Connection> poolingListener);

    /**
     * Returns a strategy which lazily creates and caches connections so that each invokation to the
     * {@link ConnectionProvider#connect(Object)} method using the same config argument results in the same
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
