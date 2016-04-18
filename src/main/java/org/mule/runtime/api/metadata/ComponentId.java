/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Represent the position of a Component inside a flow.
 * With the corresponded flow that holds it and its path inside it.
 *
 * @since 1.0
 */
public interface ComponentId
{

    /**
     * @return Flow name that holds the Component
     */
    String getFlowName();

    /**
     * @return Path of the Component inside the flow
     */
    String getComponentPath();

}
