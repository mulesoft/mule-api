/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata.descriptor.builder;

import org.mule.api.metadata.descriptor.ImmutableOperationMetadataDescriptor;
import org.mule.api.metadata.descriptor.OperationMetadataDescriptor;
import org.mule.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.api.metadata.descriptor.TypeMetadataDescriptor;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of the builder design pattern to create instances of {@link OperationMetadataDescriptor}
 *
 * @since 1.0
 */
public class OperationMetadataDescriptorBuilder
{

    private final String name;
    private List<TypeMetadataDescriptor> parameters;
    private OutputMetadataDescriptor output;
    private TypeMetadataDescriptor content;

    /**
     * Creates a new instance of {@link OperationMetadataDescriptorBuilder} with the name of the operation to describe
     *
     * @param name of the Operation to describe its metadata. This name can not be blank
     * @throws IllegalArgumentException if name is blank
     */
    OperationMetadataDescriptorBuilder(String name)
    {
        if (StringUtils.isBlank(name))
        {
            throw new IllegalArgumentException("Name parameter cannot be blank for an OperationMetadataDescriptor");
        }

        this.name = name;
    }

    /**
     * @param parameters a {@link List} of {@link TypeMetadataDescriptor} that describe the metadata for each parameter
     *                   of the operation described
     * @return the contributed descriptor builder
     */
    public OperationMetadataDescriptorBuilder withParametersDescriptor(List<TypeMetadataDescriptor> parameters)
    {
        this.parameters = parameters;
        return this;
    }

    /**
     * @param output the {@link OutputMetadataDescriptor} that describes the output of the operation
     * @return the contributed descriptor builder contributed with the {@link OutputMetadataDescriptor}
     */
    public OperationMetadataDescriptorBuilder withOutputDescriptor(OutputMetadataDescriptor output)
    {
        this.output = output;
        return this;
    }

    /**
     * @param content the {@link TypeMetadataDescriptor} that describes the metadata for the content of the operation described
     * @return the contributed descriptor builder contributed with the {@link TypeMetadataDescriptor} for content
     */
    public OperationMetadataDescriptorBuilder withContentDescriptor(TypeMetadataDescriptor content)
    {
        this.content = content;
        return this;
    }

    /**
     * @return a {@link OperationMetadataDescriptor} instance with the metadata description for the content, output, and
     * type of each of the parameters of the Operation
     * @throws IllegalArgumentException if the {@link OutputMetadataDescriptor} was not set during building
     */
    public OperationMetadataDescriptor build()
    {
        if (output == null)
        {
            throw new IllegalArgumentException("OutputMetadataDescriptor cannot be null in an OperationMetadataDescriptor");
        }

        if (parameters == null)
        {
            parameters = Collections.unmodifiableList(Collections.emptyList());
        }

        return new ImmutableOperationMetadataDescriptor(name, parameters, output, content);
    }

}
