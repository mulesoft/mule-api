/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.connection;

/**
 * Strategy to implement different connection management mechanisms.
 * <p>
 * For example, whether connections should be pooled, tied to an OAuth
 * token, cached, etc.
 *
 * @param <Connection> the generic type of the connection being managed by {@code this} instance
 * @since 1.0
 */
public interface ConnectionHandlingStrategy<Connection>
{

    /**
     * Wraps a connection into a {@link ConnectionHandler} and returns it.
     * This method is to be assumed thread-safe, but no assumptions should be made
     * on whether each invokation returns the same {@link ConnectionHandler} or if
     * that return value is wrapping the same underlying {@code Connection} instance.
     *
     * @return a {@link ConnectionHandler}
     * @throws ConnectionException if an exception was found trying to obtain the connection
     */
    ConnectionHandler<Connection> getConnectionHandler() throws ConnectionException;
}
