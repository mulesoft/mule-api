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
 * @since 1.0
 */
public interface ConnectionHandlingStrategyFactory
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
     * @param <Connection>          The generic type of the connections to be produced by the owning {@link ConnectionProvider}
     * @return a {@link ConnectionHandlingStrategy}
     */
    <Connection> ConnectionHandlingStrategy<Connection> supportsPooling(PoolingProfile defaultPoolingProfile);

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
    <Connection> ConnectionHandlingStrategy<Connection> requiresPooling(PoolingProfile defaultPoolingProfile);

    /**
     * Returns a strategy which lazily creates and caches connections so that each invokation to the
     * {@link ConnectionProvider#connect(Object)} method using the same config argument results in the same
     * connection. Invoking {@link ConnectionHandler#release()} will not close the connection.
     *
     * @param <Connection> The generic type of the connections to be produced by the owning {@link ConnectionProvider}
     * @return a {@link ConnectionHandlingStrategy}
     */
    <Connection> ConnectionHandlingStrategy<Connection> cached();

    /**
     * Creates a strategy which adds no behaviour. A new connection is created each time
     * {@link ConnectionHandlingStrategy#getConnectionHandler()} is invoked and will be closed when
     * {@link ConnectionHandler#release()} is called.
     *
     * @param <Connection> The generic type of the connections to be produced by the owning {@link ConnectionProvider}
     * @return a {@link ConnectionHandlingStrategy}
     */
    <Connection> ConnectionHandlingStrategy<Connection> none();

}
