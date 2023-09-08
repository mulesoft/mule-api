/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

/**
 * A {@link ConnectionProvider} specialization which lazily creates and caches connections.
 * <p>
 * This interface is declarative. The actual caching is not implemented on the provider itself but performed by the runtime, which
 * will correlate each invocation to the {@link #connect()} method to the component requesting the connection.
 *
 * @param <C> the generic type of the connections to be handled
 * @since 1.0
 */
public interface CachedConnectionProvider<C> extends ConnectionProvider<C> {

}
