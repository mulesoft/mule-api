/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.descriptor.ImmutableTypeMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;

/**
 * Implementation of the builder design pattern to create instances of {@link TypeMetadataDescriptor}
 *
 * @since 1.0
 */
public class TypeMetadataDescriptorBuilder
{

    private MetadataType type;

    /**
     * Creates a new instance of the builder.
     */
    TypeMetadataDescriptorBuilder()
    {
    }

    /**
     * Indicates that the component will be of type {@param type}
     *
     * @param type of the component parameter
     * @return the instance builder contributed with a {@link MetadataType}
     */
    public TypeMetadataDescriptorBuilder withType(MetadataType type)
    {
        this.type = type;
        return this;
    }

    /**
     * Builds and creates the descriptor of the component.
     * <p>
     * Validates that the type is not null.
     *
     * @return a {@link TypeMetadataDescriptor} with the metadata description of the component.
     * @throws IllegalArgumentException if type was not set during building
     */
    public TypeMetadataDescriptor build()
    {
        if (type == null)
        {
            throw new IllegalArgumentException("Type parameter cannot be null for ParameterMetadataDescriptor");
        }

        return new ImmutableTypeMetadataDescriptor(type);
    }

}
