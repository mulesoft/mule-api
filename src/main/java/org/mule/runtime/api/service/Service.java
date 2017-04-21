/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.service;

import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.api.meta.NamedObject;

/**
 * Defines a named service which is instantiated at the container level.
 * <p>
 * This interface is intended to be used to implement services that are common enough to make them available on the container so
 * other Mule artifacts can make use of them without having to re-implement them.
 * <p>
 * Service implementations can implement lifecycle interfaces {@link Startable} and {@link Stoppable}. Lifecycle will be applied
 * when the container is started/stopped.
 * <p>
 * Implementations may overload methods from the Service interface with {@link javax.inject.Inject @Inject} and add any parameters
 * to be resolved from the Mule application context. When such invocation is made, those injected parameters cannot be null.
 * 
 * @since 1.0
 */
public interface Service extends NamedObject {
}
