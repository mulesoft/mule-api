/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import org.mule.metadata.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the builder design pattern to create a new {@link MetadataKey} instance.
 *
 * @since 1.0
 */
public final class MetadataKeyBuilder
{

    private final Map<Class<? extends MetadataProperty>, MetadataProperty> properties = new HashMap<>();
    private final String id;
    private String displayName;

    private MetadataKeyBuilder(String id)
    {
        this.id = id;
    }

    /**
     * Creates and returns new instance of a {@link MetadataKeyBuilder}, to help building a new {@link MetadataKey}
     * represented by the given {@param id}
     *
     * @param id of the {@link MetadataKey} to be created
     * @return an initialized instance of {@link MetadataKeyBuilder}
     */
    public static MetadataKeyBuilder newKey(String id)
    {
        return new MetadataKeyBuilder(id);
    }

    /**
     * Adds a display name to the {@link MetadataKey} that is being built
     *
     * @param displayName of the {@link MetadataKey} to be created
     * @return the builder with the configured display name
     */
    public MetadataKeyBuilder withDisplayName(String displayName)
    {
        this.displayName = displayName;
        return this;
    }

    /**
     * Add a custom {@link MetadataProperty} to the {@link MetadataKey} that is being built
     *
     * @param property The new {@link MetadataProperty} to be added into the {@link MetadataKey}
     * @return the builder with the new
     */
    public MetadataKeyBuilder withProperty(MetadataProperty property)
    {
        if (properties.get(property.getClass()) != null)
        {
            throw new IllegalArgumentException(String.format("Cannot add a %s type metadata property, there is already one of that type defined",
                                                             property.getClass()));
        }

        properties.put(property.getClass(), property);
        return this;
    }

    /**
     * Builds a new instance of {@link MetadataKey}.
     *
     * @return an initialized {@link MetadataKey}
     */
    public MetadataKey build()
    {
        return new DefaultMetadataKey(id, StringUtils.isNotEmpty(displayName) ? displayName : id, properties);
    }
}
