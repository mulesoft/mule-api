/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

/**
 * Handles connections of the generic {@code Connection} type, which are created from a configuration object of the generic type
 * {@code Config}.
 * <p>
 * Providers centralize the logic and parametrization to provision and release connection, while remaining abstracted from the
 * concerns of actually manage those connections.
 * <p>
 * Implementations are expected to be reusable and thread-safe.
 * <p>
 * It is valid for implementations to implement any of the lifecycle interfaces or to request external dependencies through any of
 * the JSR-330 annotations. However, implementations are not propagate lifecycle or to perform dependency injection into the
 * generated connections. The runtime will do that automatically at the proper time.
 *
 * @param <C> the generic type of the connections to be handled
 * @since 1.0
 */
public interface ConnectionProvider<C> {

  /**
   * Creates a new connection.
   * <p>
   * The returned connection is expected to be ready to use
   *
   * @return a ready to use {@code Connection}
   */
  C connect() throws ConnectionException;

  /**
   * Disposes the given {@code connection}, freeing all its allocated resources
   *
   * @param connection a non {@code null} {@code Connection}.
   */
  void disconnect(C connection);

  /**
   * Validates the given {@link C}.
   *
   * In invalid connection case, the {@link ConnectionValidationResult} should also return a valid message
   * {@link ConnectionValidationResult#getMessage()}, exception {@link ConnectionValidationResult#getException()} and code
   * {@link ConnectionValidationResult#getErrorType()}
   *
   * @param connection a non {@code null} {@link C}.
   * @return a {@link ConnectionValidationResult} indicating if the connection is valid or not.
   */
  ConnectionValidationResult validate(C connection);
}
