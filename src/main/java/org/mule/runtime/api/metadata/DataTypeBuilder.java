/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.el.ExpressionFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides a way to build immutable {@link DataType} objects.
 * <p>
 * Implementations need not be thread safe.
 *
 * @since 1.0
 */
@NoImplement
public interface DataTypeBuilder extends DataTypeParamsBuilder {

  /**
   * Sets the given type for the {@link DataType} to be built. See {@link DataType#getType()}.
   * 
   * @param type the java type to set.
   * @return this builder.
   */
  DataTypeParamsBuilder type(Class<?> type);

  /**
   * Sets the given type for the {@link CollectionDataType} to be built. See {@link CollectionDataType#getType()}.
   * 
   * @param iteratorType the java collection type to set.
   * @return this builder.
   * @throws IllegalArgumentException if the given collectionType is not a descendant of {@link Iterator}.
   */
  DataTypeCollectionTypeBuilder streamType(Class<? extends Iterator> iteratorType);

  /**
   * Sets the given type for the {@link CollectionDataType} to be built. See {@link CollectionDataType#getType()}.
   * 
   * @param collectionType the java collection type to set.
   * @return this builder.
   * @throws IllegalArgumentException if the given collectionType is not a descendant of {@link Collection}.
   */
  DataTypeCollectionTypeBuilder collectionType(Class<? extends Collection> collectionType);

  /**
   * Down-casts the builder to {@link DataTypeCollectionTypeBuilder}, allowing the builder to be used in a fluent way without
   * having to cast it when dealing with {@link Iterable}s.
   *
   * @return this builder.
   */
  DataTypeCollectionTypeBuilder asCollectionTypeBuilder();

  /**
   * Sets the given type for the {@link FunctionDataType} to be built.
   *
   * @param functionType the java {@link ExpressionFunction} type to set
   * @return this builder
   */
  DataTypeFunctionTypeBuilder functionType(Class<? extends ExpressionFunction> functionType);

  /**
   * Down-casts the builder to {@link DataTypeFunctionTypeBuilder}, allowing the builder to be used in a fluent way without having
   * to cast it when dealing with {@link ExpressionFunction}s.
   *
   * @return this builder.
   */
  DataTypeFunctionTypeBuilder asFunctionTypeBuilder();

  /**
   * Sets the given type for the {@link MapDataType} to be built.
   *
   * @param mapType the java map type to set
   * @return this builder
   */
  DataTypeMapTypeBuilder mapType(Class<? extends Map> mapType);

  /**
   * Down-casts the builder to {@link DataTypeMapTypeBuilder}, allowing the builder to be used in a fluent way without having to
   * cast it when dealing with {@link Map}s.
   *
   * @return this builder.
   */
  DataTypeMapTypeBuilder asMapTypeBuilder();

  /**
   * Populates the builder from the given {@code value}.
   * <p>
   * This method will get the {@code type}, {@code mimeType} and {@code encoding} from the given {@code value} according to its
   * concrete type.
   * <p>
   *
   * @param value an object instance.
   * @return this builder.
   */
  DataTypeParamsBuilder fromObject(Object value);

  /**
   * Populates the builder from the given {@link ExpressionFunction}.
   *
   * @param function the {@link ExpressionFunction} to use
   * @return this builder
   */
  DataTypeFunctionTypeBuilder fromFunction(ExpressionFunction function);

  /**
   * Provides methods to set data associated to the items of a {@link Collection}, when the type is a {@link Collection}.
   */
  interface DataTypeCollectionTypeBuilder extends DataTypeParamsBuilder {

    /**
     * Sets the given {@code itemType} for the {@link DataType} to be built, when the type is an {@link Iterable}.
     * 
     * @param itemType the java type to set.
     * @return this builder.
     */
    DataTypeCollectionTypeBuilder itemType(Class<?> itemType);

    /**
     * Sets the given {@code itemMediaType} for the {@link DataType} to be built., when the type is an {@link Iterable}.
     * 
     * @param itemMediaType the media type string to set
     * @return this builder.
     */
    DataTypeCollectionTypeBuilder itemMediaType(String itemMediaType);

    /**
     * Sets the given {@code itemMediaType} for the {@link DataType} to be built, when the type is an {@link Iterable}.
     * <p>
     * If the media type for the given string has a {@code charset} parameter, that will be set as the encoding for the items's
     * {@link DataType} being built, unless it had been previously set.
     * 
     * @param itemMediaType the media type to set. If null, the builder is not changed.
     * @return this builder.
     */
    DataTypeCollectionTypeBuilder itemMediaType(MediaType itemMediaType);

  }

  /**
   * Provides methods to set data associated to the resources of an {@link ExpressionFunction}.
   */
  interface DataTypeFunctionTypeBuilder extends DataTypeParamsBuilder {

    /**
     * Sets the return type of the function, {@code null} indicates there's none and is the default value.
     *
     * @param returnType the {@link DataType} of the functions return type
     * @return this builder.
     */
    DataTypeFunctionTypeBuilder returnType(DataType returnType);

    /**
     * Sets the functions parameters types, represented by {@link FunctionParameter}s.
     *
     * @param parametersTypes a {@link List} of {@link FunctionParameter}s.
     * @return this builder.
     */
    DataTypeFunctionTypeBuilder parametersType(List<FunctionParameter> parametersTypes);

  }

  /**
   * Provides methods to set data associated to the keys and values of a {@link Map}, when the type is a {@link Map}.
   */
  interface DataTypeMapTypeBuilder extends DataTypeParamsBuilder {

    /**
     * Sets the given {@code keyType} for the {@link DataType} to be built, when the type is a {@link Map}.
     *
     * @param keyType the java type to set.
     * @return this builder.
     */
    DataTypeMapTypeBuilder keyType(Class<?> keyType);

    /**
     * Sets the given {@code keyMediaType} for the {@link DataType} to be built, when the type is a {@link Map}.
     *
     * @param keyMediaType the media type string to set
     * @return this builder.
     */
    DataTypeMapTypeBuilder keyMediaType(String keyMediaType);

    /**
     * Sets the given {@code keyMediaType} for the {@link DataType} to be built, when the type is a {@link Map}.
     * <p>
     * If the media type for the given string has a {@code charset} parameter, that will be set as the encoding for the key's
     * {@link DataType} being built, unless it had been previously set.
     *
     * @param keyMediaType the media type to set. If null, the builder is not changed.
     * @return this builder.
     */
    DataTypeMapTypeBuilder keyMediaType(MediaType keyMediaType);

    /**
     * Sets the given {@code valueType} for the {@link DataType} to be built, when the type is a {@link Map}.
     *
     * @param valueType the java type to set.
     * @return this builder.
     */
    DataTypeMapTypeBuilder valueType(Class<?> valueType);

    /**
     * Sets the given {@code valueMediaType} for the {@link DataType} to be built, when the type is a {@link Map}.
     *
     * @param valueMediaType the media type string to set
     * @return this builder.
     */
    DataTypeMapTypeBuilder valueMediaType(String valueMediaType);

    /**
     * Sets the given {@code valueMediaType} for the {@link DataType} to be built, when the type is a {@link Map}.
     * <p>
     * If the media type for the given string has a {@code charset} parameter, that will be set as the encoding for the key's
     * {@link DataType} being built, unless it had been previously set.
     *
     * @param valueMediaType the media type to set. If null, the builder is not changed.
     * @return this builder.
     */
    DataTypeMapTypeBuilder valueMediaType(MediaType valueMediaType);

  }
}
