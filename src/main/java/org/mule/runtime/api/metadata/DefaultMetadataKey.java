/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Default immutable implementation for the {@link MetadataKey}.
 *
 * @since 1.0
 */
public final class DefaultMetadataKey implements MetadataKey
{

    private final String id;
    private final String displayName;
    private final Set<MetadataKey> childs;
    private final Map<Class<? extends MetadataProperty>, MetadataProperty> properties;

    public DefaultMetadataKey(String id, String displayName, Set<MetadataProperty> properties, Set<MetadataKey> childs)
    {
        this.id = id;
        this.displayName = displayName;
        this.childs = childs;
        this.properties = unmodifiableMap(properties.stream().collect(toMap(MetadataProperty::getClass, p -> p)));
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
    public Set<MetadataKey> getChilds()
    {
        return unmodifiableSet(childs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends MetadataProperty> Optional<T> getMetadataProperty(Class<T> propertyType)
    {
        return ofNullable((T) properties.get(propertyType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MetadataProperty> getProperties()
    {
        return unmodifiableSet(new HashSet<>(properties.values()));
    }

    @Override
    public int hashCode()
    {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return reflectionEquals(this, obj);
    }
}
