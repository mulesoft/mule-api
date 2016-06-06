/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.stream.Collectors.toSet;
import static org.mule.metadata.utils.StringUtils.isNotEmpty;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract {@link MetadataKey} builder which provides the basic behaviour for the construction of a {@link MetadataKey}
 *
 * @param <B> Concrete builder type that extends {@link BaseMetadataKeyBuilder}
 * @since 4.0
 */
public abstract class BaseMetadataKeyBuilder<B extends BaseMetadataKeyBuilder>
{

    private static final String EMPTY_NAME = "";
    protected final Set<MetadataProperty> properties = new HashSet<>();
    protected final String id;
    protected String displayName;
    protected Set<B> childs = new HashSet<>();

    /**
     * Creates a new instance of the abstract builder
     *
     * @param id of the {@link MetadataKey}
     */
    protected BaseMetadataKeyBuilder(String id)
    {
        this.id = id;
    }

    /**
     * Adds a display name to the {@link MetadataKey} that is being built
     *
     * @param displayName of the {@link MetadataKey} to be created
     * @return {@code this} builder with the configured display name
     */
    public B withDisplayName(String displayName)
    {
        this.displayName = displayName;
        return (B) this;
    }

    /**
     * Adds a custom {@link MetadataProperty} to the {@link MetadataKey} that is being built
     *
     * @param property The new {@link MetadataProperty} to be added into the {@link MetadataKey}
     * @return {@code this} builder with the new property
     */
    public B withProperty(MetadataProperty property)
    {
        if (properties.stream().anyMatch(p -> p.getClass().equals(property.getClass())))
        {
            throw new IllegalArgumentException(String.format("The key %s already contains a metadata property of type %s", id, property.getClass().getName()));
        }

        properties.add(property);
        return (B) this;
    }

    /**
     * Adds a new {@link MetadataKeyBuilder} child to the {@link MetadataKey} that is being built.
     *
     * @param metadataKeyBuilder the {@link MetadataKeyBuilder} that is used to create the instance of the new child.
     * @return {@code this} builder with a new child.
     */
    public B withChild(B metadataKeyBuilder)
    {
        childs.add(metadataKeyBuilder);
        return (B) this;
    }

    /**
     * Builds a new instance of {@link MetadataKey}.
     *
     * @return an initialized {@link MetadataKey}
     */
    public MetadataKey build()
    {
        String name = isNotEmpty(displayName) ? displayName : id;
        return new DefaultMetadataKey(id, name, properties, childs.stream().map(B::build).collect(toSet()), EMPTY_NAME);
    }
}
