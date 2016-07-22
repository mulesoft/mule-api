/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.service;

import org.mule.runtime.api.meta.NamedObject;

/**
 * Defines a named service.
 * <p/>
 * Service implementations can implement lifecycle interfaces {@link org.mule.runtime.core.api.lifecycle.Startable}
 * and {@link org.mule.runtime.core.api.lifecycle.Stoppable}
 *
 * @since 1.0
 */
public interface Service extends NamedObject
{
}
