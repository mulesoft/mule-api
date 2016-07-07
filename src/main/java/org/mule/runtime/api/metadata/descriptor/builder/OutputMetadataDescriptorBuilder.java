/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import static org.mule.runtime.api.metadata.descriptor.builder.MetadataDescriptorBuilder.typeDescriptor;
import static org.mule.runtime.api.metadata.resolving.MetadataResult.*;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.MuleMessage;
import org.mule.runtime.api.metadata.MetadataAware;
import org.mule.runtime.api.metadata.descriptor.ImmutableOutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * Implementation of the builder design pattern to create instances of {@link OutputMetadataDescriptor}
 *
 * @since 1.0
 */
public class OutputMetadataDescriptorBuilder
{

    private MetadataResult<TypeMetadataDescriptor> returnTypeResult;
    private MetadataResult<TypeMetadataDescriptor> attributes;

    /**
     * Creates a new instance of {@link OutputMetadataDescriptorBuilder}
     */
    OutputMetadataDescriptorBuilder()
    {
    }

    /**
     * Adds a {@link MetadataResult} of {@link TypeMetadataDescriptor} {@param returnTypeResult} that describes the
     * return type of the component.
     *
     * @param returnTypeResult a {@link MetadataResult} of {@link TypeMetadataDescriptor} describing the component output return type
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for return type
     */
    public OutputMetadataDescriptorBuilder withReturnType(MetadataResult<TypeMetadataDescriptor> returnTypeResult)
    {
        this.returnTypeResult = returnTypeResult;
        return this;
    }

    /**
     * Adds a {@link MetadataResult} of {@link TypeMetadataDescriptor} {@param returnTypeResult} that describes the
     * he output {@link MuleMessage#getAttributes} {@link MetadataType}.
     *
     * @param attributesTypeResult a {@link MetadataResult} of {@link TypeMetadataDescriptor} describing the component output attributes type.
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for message attributes
     */
    public OutputMetadataDescriptorBuilder withAttributesType(MetadataResult<TypeMetadataDescriptor> attributesTypeResult)
    {
        this.attributes = attributesTypeResult;
        return this;
    }

    /**
     * Describes that the return type of the component will be of {@link MetadataType} {@param returnTypeResult}
     *
     * @param returnType of the component returnTypeResult output
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for return type
     */
    public OutputMetadataDescriptorBuilder withReturnType(MetadataType returnType)
    {
        this.returnTypeResult = success(typeDescriptor().withType(returnType).build());
        return this;
    }

    /**
     * Describes that the output {@link MuleMessage#getAttributes} {@link MetadataType}
     * of the component will be {@param attributesTypeResult}
     *
     * @param attributesType of the component output attributes
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for message attributes
     */
    public OutputMetadataDescriptorBuilder withAttributesType(MetadataType attributesType)
    {
        this.attributes = success(typeDescriptor().withType(attributesType).build());
        return this;
    }

    /**
     * @return a {@link OutputMetadataDescriptor} instance with the metadata description for the output of
     * a {@link MetadataAware} component
     * @throws IllegalArgumentException if the {@link MuleMessage#getPayload} or {@link MuleMessage#getAttributes} were
     *                                  not set during building
     */
    public OutputMetadataDescriptor build()
    {
        if (returnTypeResult == null)
        {
            throw new IllegalArgumentException("Payload type parameter cannot be null for OutputMetadataDescriptor");
        }
        if (attributes == null)
        {
            throw new IllegalArgumentException("Attributes type parameter cannot be null for OutputMetadataDescriptor");
        }

        return new ImmutableOutputMetadataDescriptor(returnTypeResult, attributes);
    }

}
