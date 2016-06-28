/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.util.Collection;

/**
 * Provides a way to build immutable {@link DataType} objects.
 * <p>
 * Implementations need not be thread safe.
 *
 * @since 1.0
 */
public interface DataTypeBuilder<T> extends DataTypeParamsBuilder<T>
{

    /**
     * Sets the given type for the {@link DataType} to be built. See {@link DataType#getType()}.
     * 
     * @param type the java type to set.
     * @return this builder.
     */
    <N> DataTypeParamsBuilder<N> type(Class<N> type);

    /**
     * Sets the given type for the {@link CollectionDataType} to be built. See
     * {@link CollectionDataType#getType()}.
     * 
     * @param collectionType the java collection type to set.
     * @return this builder.
     * @throws IllegalArgumentException if the given collectionType is not a descendant of
     *             {@link Collection}.
     */
    <N extends Collection<I>, I> DataTypeCollectionTypeBuilder<N> collectionType(Class<N> collectionType);

    /**
     * Populates the builder from the given {@code value}.
     * <p>
     * This method will get the {@code type}, {@code mimeType} and {@code encoding} from the given {@code value}
     * according to its concrete type.
     * <p>
     *
     * @param value an object instance.
     * @return this builder.
     */
    DataTypeParamsBuilder<T> fromObject(T value);

    /**
     * Provides methods to set data associated to the items of a {@link Collection}, when the type
     * is a {@link Collection}.
     *
     * @param <T>
     */
    interface DataTypeCollectionTypeBuilder<T> extends DataTypeParamsBuilder<T>
    {

        /**
         * Sets the given {@code itemType} for the {@link DataType} to be built, when the type is a
         * {@link Collection}.
         * 
         * @param itemType the java type to set.
         * @return this builder.
         */
        <I> DataTypeCollectionTypeBuilder<T> itemType(Class<I> itemType);

        /**
         * Sets the given {@code itemMediaType} for the {@link DataType} to be built., when the type
         * is a {@link Collection}.
         * 
         * @param itemMediaType the media type string to set
         * @return this builder.
         */
        <I> DataTypeCollectionTypeBuilder<T> itemMediaType(String itemMediaType);

        /**
         * Sets the given {@code itemMediaType} for the {@link DataType} to be built, when the type
         * is a {@link Collection}.
         * <p>
         * If the media type for the given string has a {@code charset} parameter, that will be set
         * as the encoding for the items's {@link DataType} being built, unless it had been
         * previously set.
         * 
         * @param itemMediaType the media type to set. If null, the builder is not changed.
         * @return this builder.
         */
        <I> DataTypeCollectionTypeBuilder<T> itemMediaType(MediaType itemMediaType);

    }

}
