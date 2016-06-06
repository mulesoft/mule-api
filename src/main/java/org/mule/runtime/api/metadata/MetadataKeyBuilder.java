/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * An implementation of the builder design pattern to create a new {@link MetadataKey} instance.
 *
 * @since 1.0
 */
public final class MetadataKeyBuilder extends BaseMetadataKeyBuilder<MetadataKeyBuilder>
{

    private MetadataKeyBuilder(String id)
    {
        super(id);
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
}