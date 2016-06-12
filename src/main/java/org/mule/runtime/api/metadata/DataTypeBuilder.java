/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.message.MuleMessage;

import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * Provides a way to build immutable {@link DataType} objects.
 *
 * @since 1.0
 */
public interface DataTypeBuilder<T> extends DataTypeOptionalParamsBuilder<T>
{

    /**
     * Sets the given type for the {@link DataType} to be built. See {@link DataType#getType()}.
     * 
     * @param type the java type to set.
     * @return this builder.
     */
    DataTypeOptionalParamsBuilder<T> type(Class<T> type);

    /**
     * Sets the given type for the {@link CollectionDataType} to be built. See
     * {@link CollectionDataType#getType()}.
     * 
     * @param collectionType the java collection type to set.
     * @return this builder.
     * @throws IllegalArgumentException if the given collectionType is not a descendant of
     *             {@link Collection}.
     */
    <I> DataTypeCollectionTypeBuilder<T> collectionType(Class<T> collectionType);

    /**
     * Populates the builder from the given {@code value}.
     * <p>
     * This method will get the {@code javaType} and {@code mimeType} given {@code value} according
     * to its concrete type.
     * <p>
     * This is useful for using existing {@link DataType}s as templates for new ones. Just copying
     * them is not necessary since those are immutable.
     *
     * @param value an object instance. This can be a {@link MuleMessage}, a {@link Collection}, a
     *            {@link Proxy} instance or any other object.
     * @return this builder.
     */
    DataTypeBuilder<T> from(T value);

    interface DataTypeCollectionTypeBuilder<T> extends DataTypeOptionalParamsBuilder<T>
    {

        /**
         * Sets the given item type for the {@link CollectionDataType} to be built. See
         * {@link CollectionDataType#getItemType()}.
         * 
         * @param itemType the java type to set.
         * @return this builder.
         */
        <I> DataTypeOptionalParamsBuilder<T> itemType(Class<I> itemType);

    }

}
