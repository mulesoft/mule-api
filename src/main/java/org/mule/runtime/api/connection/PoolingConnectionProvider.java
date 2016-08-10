/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

/**
 * A {@link ConnectionProvider} specialization which pools connections.
 * <p>
 * This interface is declarative. The actual pooling is not implemented on the provider
 * itself but performed by the runtime.
 * <p>
 * This interface also extends {@link PoolingListener}, allowing to perform operations
 * when a connection is borrow and/or returned to the pool.
 *
 * @param <C> the generic type of the connections to be handled
 * @since 1.0
 */
//TODO: MULE-10174: Review where do ConnectionProvide specializations belong
public interface PoolingConnectionProvider<C> extends ConnectionProvider<C>, PoolingListener<C> {

}
