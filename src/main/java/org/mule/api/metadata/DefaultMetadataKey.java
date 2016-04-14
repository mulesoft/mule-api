/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.api.metadata;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final Map<Class<? extends MetadataProperty>, MetadataProperty> properties;

    public DefaultMetadataKey(String id, String displayName, Set<MetadataProperty>  properties)
    {
        this.id = id;
        this.displayName = displayName;
        this.properties = properties.stream().collect(Collectors.toMap(MetadataProperty::getClass, p -> p));
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
    public <T extends MetadataProperty> Optional<T> getMetadataProperty(Class<T> propertyType)
    {
        return Optional.ofNullable((T) properties.get(propertyType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MetadataProperty> getProperties()
    {
        return Collections.unmodifiableSet(new HashSet<>(properties.values()));
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof DefaultMetadataKey
               && ((DefaultMetadataKey) obj).getId().equals(this.id);
    }
}
