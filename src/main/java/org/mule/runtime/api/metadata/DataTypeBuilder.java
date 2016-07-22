/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.util.Collection;
import java.util.Iterator;

/**
 * Provides a way to build immutable {@link DataType} objects.
 * <p>
 * Implementations need not be thread safe.
 *
 * @since 1.0
 */
public interface DataTypeBuilder extends DataTypeParamsBuilder
{

    /**
     * Sets the given type for the {@link DataType} to be built. See {@link DataType#getType()}.
     * 
     * @param type the java type to set.
     * @return this builder.
     */
    DataTypeParamsBuilder type(Class<?> type);

    /**
     * Sets the given type for the {@link CollectionDataType} to be built. See
     * {@link CollectionDataType#getType()}.
     * 
     * @param iteratorType the java collection type to set.
     * @return this builder.
     * @throws IllegalArgumentException if the given collectionType is not a descendant of
     *             {@link Iterator}.
     */
    DataTypeCollectionTypeBuilder streamType(Class<? extends Iterator> iteratorType);
    
    /**
     * Sets the given type for the {@link CollectionDataType} to be built. See
     * {@link CollectionDataType#getType()}.
     * 
     * @param collectionType the java collection type to set.
     * @return this builder.
     * @throws IllegalArgumentException if the given collectionType is not a descendant of
     *             {@link Collection}.
     */
    DataTypeCollectionTypeBuilder collectionType(Class<? extends Collection> collectionType);

    /**
     * Down-casts the builder to {@link DataTypeCollectionTypeBuilder}, allowing the builder to be used in a fluent way
     * without having to cast it when dealing with {@link Iterable}s.
     *
     * @return this builder.
     */
    DataTypeCollectionTypeBuilder asCollectionTypeBuilder();

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
    DataTypeParamsBuilder fromObject(Object value);

    /**
     * Provides methods to set data associated to the items of a {@link Collection}, when the type
     * is a {@link Collection}.
     *
     * @param <T>
     */
    interface DataTypeCollectionTypeBuilder extends DataTypeParamsBuilder
    {

        /**
         * Sets the given {@code itemType} for the {@link DataType} to be built, when the type is an
         * {@link Iterable}.
         * 
         * @param itemType the java type to set.
         * @return this builder.
         */
        DataTypeCollectionTypeBuilder itemType(Class<?> itemType);

        /**
         * Sets the given {@code itemMediaType} for the {@link DataType} to be built., when the type
         * is an {@link Iterable}.
         * 
         * @param itemMediaType the media type string to set
         * @return this builder.
         */
        DataTypeCollectionTypeBuilder itemMediaType(String itemMediaType);

        /**
         * Sets the given {@code itemMediaType} for the {@link DataType} to be built, when the type
         * is an {@link Iterable}.
         * <p>
         * If the media type for the given string has a {@code charset} parameter, that will be set
         * as the encoding for the items's {@link DataType} being built, unless it had been
         * previously set.
         * 
         * @param itemMediaType the media type to set. If null, the builder is not changed.
         * @return this builder.
         */
        DataTypeCollectionTypeBuilder itemMediaType(MediaType itemMediaType);

    }

}
