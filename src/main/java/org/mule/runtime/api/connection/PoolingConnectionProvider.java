/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.connection;

/**
 * A {@link ConnectionProvider} specialization which pools connections.
 * <p>
 * This interface is declarative. The actual pooling is not implemented on the provider itself but performed by the runtime.
 * <p>
 * This interface also extends {@link PoolingListener}, allowing to perform operations when a connection is borrow and/or returned
 * to the pool.
 *
 * @param <C> the generic type of the connections to be handled
 * @since 1.0
 */
public interface PoolingConnectionProvider<C> extends ConnectionProvider<C>, PoolingListener<C> {

}
