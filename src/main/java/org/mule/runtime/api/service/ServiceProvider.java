/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.service;

import java.util.List;

/**
 * Provides service definitions.
 *
 * @since 1.0
 */
public interface ServiceProvider
{

    /**
     * Provides service instances.
     * <p/>
     * A service provider can return different service definitions depending on the execution
     * environment.
     *
     * @return the service definitions provided by this instance. Non null.
     */
    List<ServiceDefinition> providedServices();
}
