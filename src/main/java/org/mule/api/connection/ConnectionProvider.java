/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.connection;

/**
 * Handles connections of the generic {@code Connection} type, which are created from a
 * configuration object of the generic type {@code Config}.
 * <p>
 * Providers centralize the logic and parametrization to provision and release
 * connection, while remaining abstracted from the concerns of actually manage
 * those connections.
 * <p>
 * Implementations are expected to be reusable and thread-safe.
 * <p>
 * It is valid for implementations to implement any of the lifecycle interfaces
 * or to request external dependencies through any of the JSR-330 annotations.
 * However, implementations are not propagate lifecycle or to perform dependency
 * injection into the generated connections. The runtime will do that automatically
 * at the proper time.
 *
 * @param <Config>     the generic type of the objects to be used as configs
 * @param <Connection> the generic type of the connections to be handled
 * @since 1.0
 */
public interface ConnectionProvider<Config, Connection>
{

    /**
     * Creates a new connection from the given {@code config}.
     * <p>
     * The returned connection is expected to be ready to use
     *
     * @param config a configuration which parametrizes the connection's creation
     * @return a ready to use {@code Connection}
     */
    Connection connect(Config config) throws ConnectionException;

    /**
     * Disposes the given {@code connection}, freeing all its allocated resources
     *
     * @param connection a non {@code null} {@code Connection}.
     */
    void disconnect(Connection connection);

    /**
     * Specifies the {@link ConnectionHandlingStrategy} that should be use to manage
     * the connections generated through the {@link #connect(Object)} method.
     * <p>
     * Implementations <b>MUST</b> return instances created through the provided
     * {@code handlingStrategyFactory}. Those instances are not to be wrapped,
     * decorated, or by any other means tampered with.
     * <p>
     * This method must also function correctly without dependencies or preconditions.
     * That means that although it is valid for {@code this} provider to have lifecycle
     * or external dependencies injected, this particular method must behave correctly
     * on every possible state.
     *
     * @param handlingStrategyFactory the {@link ConnectionHandlingStrategyFactory} to be used to generate the response value
     * @return a not {@code null} {@link ConnectionHandlingStrategy}
     */
    ConnectionHandlingStrategy<Connection> getHandlingStrategy(ConnectionHandlingStrategyFactory handlingStrategyFactory);
}
