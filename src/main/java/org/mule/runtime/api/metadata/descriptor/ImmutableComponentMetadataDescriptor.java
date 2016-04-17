/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import java.util.List;
import java.util.Optional;

/**
 * Immutable concrete implementation of {@link ComponentMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ImmutableComponentMetadataDescriptor implements ComponentMetadataDescriptor
{

    private final String name;
    private final List<TypeMetadataDescriptor> parameters;
    private final OutputMetadataDescriptor outputParameter;
    private final TypeMetadataDescriptor content;

    public ImmutableComponentMetadataDescriptor(String name, List<TypeMetadataDescriptor> parameters,
                                                OutputMetadataDescriptor outputParameter,
                                                TypeMetadataDescriptor content)
    {
        this.name = name;
        this.parameters = parameters;
        this.outputParameter = outputParameter;
        this.content = content;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeMetadataDescriptor> getParametersMetadata()
    {
        return this.parameters;
    }

    @Override
    public Optional<TypeMetadataDescriptor> getContentMetadata()
    {
        return Optional.ofNullable(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputMetadataDescriptor getOutputMetadata()
    {
        return this.outputParameter;
    }
}
