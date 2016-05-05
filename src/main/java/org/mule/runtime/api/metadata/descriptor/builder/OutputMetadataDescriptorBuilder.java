/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import org.mule.runtime.api.metadata.MetadataAware;
import org.mule.runtime.api.metadata.descriptor.ImmutableOutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.message.MuleMessage;
import org.mule.metadata.api.model.MetadataType;

/**
 * Implementation of the builder design pattern to create instances of {@link OutputMetadataDescriptor}
 *
 * @since 1.0
 */
public class OutputMetadataDescriptorBuilder
{

    private TypeMetadataDescriptor payload;
    private TypeMetadataDescriptor attributes;

    /**
     * Creates a new instance of {@link OutputMetadataDescriptorBuilder}
     */
    OutputMetadataDescriptorBuilder()
    {
    }

    /**
     * Describes that the return type of the component will be of {@link MetadataType} {@param payloadType}
     *
     * @param payloadType of the component payload output
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for return type
     */
    public OutputMetadataDescriptorBuilder withReturnType(MetadataType payloadType)
    {
        this.payload = MetadataDescriptorBuilder.typeDescriptor("Message.Payload")
                .withType(payloadType)
                .build();
        return this;
    }

    /**
     * Describes that the output {@link MuleMessage#getAttributes} {@link MetadataType} of the component will be {@param attributesType}
     *
     * @param attributesType of the component output attributes
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for message attributes
     */
    public OutputMetadataDescriptorBuilder withAttributesType(MetadataType attributesType)
    {
        this.attributes = MetadataDescriptorBuilder.typeDescriptor("Message.Attributes")
                .withType(attributesType)
                .build();
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
        if (payload == null)
        {
            throw new IllegalArgumentException("Payload type parameter cannot be null for OutputMetadataDescriptor");
        }
        if (attributes == null)
        {
            throw new IllegalArgumentException("Attributes type parameter cannot be null for OutputMetadataDescriptor");
        }

        return new ImmutableOutputMetadataDescriptor(payload, attributes);
    }

}
