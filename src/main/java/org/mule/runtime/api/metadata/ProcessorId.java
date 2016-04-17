/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Immutable implementation of {@link ComponentId} for identifying MessageProcessors
 *
 * @since 1.0
 */
public final class ProcessorId implements ComponentId
{

    private final String flowName;
    private final String componentPath;

    public ProcessorId(String flowName, String componentPath)
    {
        this.flowName = flowName;
        this.componentPath = componentPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlowName()
    {
        return flowName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getComponentPath()
    {
        return componentPath;
    }


    @Override
    public String toString()
    {
        return "ProcessorId{" +
               "flowName='" + flowName + '\'' +
               ", componentPath=" + componentPath +
               '}';
    }
}
