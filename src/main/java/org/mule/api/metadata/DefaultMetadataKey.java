/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Default immutable implementation for the {@link MetadataKey}.
 *
 * @since 1.0
 */
public final class DefaultMetadataKey implements MetadataKey
{

    private final String id;
    private final String displayName;
    private final Map<String, String> properties;

    public DefaultMetadataKey(String id, String displayName, Map<String, String> properties)
    {
        this.id = id;
        this.properties = properties;
        this.displayName = displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getProperty(String propertyId)
    {
        return Optional.ofNullable(properties.get(propertyId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getProperties()
    {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof DefaultMetadataKey)
        {
            DefaultMetadataKey key = (DefaultMetadataKey) obj;
            return key.getDisplayName().equals(this.displayName)
                   && key.getId().equals(this.id)
                   && key.getProperties().equals(this.properties);

        }
        return false;
    }
}
