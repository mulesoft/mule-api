/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata.descriptor;

import org.mule.metadata.api.model.MetadataType;

/**
 * Immutable concrete implementation of a {@link TypeMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ImmutableTypeMetadataDescriptor implements TypeMetadataDescriptor
{

    private final String name;
    private final MetadataType type;

    public ImmutableTypeMetadataDescriptor(String name, MetadataType type)
    {
        this.name = name;
        this.type = type;
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
    public MetadataType getType()
    {
        return type;
    }
}
