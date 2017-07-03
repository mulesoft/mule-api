/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static org.mule.runtime.api.metadata.DataType.fromObject;

import java.io.Serializable;

/**
 * Maintains a value that has an associated {@link DataType}.
 *
 * @param <T> the content type.
 * @since 1.0
 */
public final class TypedValue<T> implements Serializable {

  private static final long serialVersionUID = -2533879516750283994L;

  /**
   * Utility method to obtain a type value's content, in cases in which you don't know if
   * the {@ccode value} is a type value at all.
   * <p>
   * If {@code value} is a TypeValue, then {@link #getValue()} is returned. Otherwise,
   * the {@code value} is returned as is (even if it's {@code null})
   *
   * @param value a value which may or may not be a TypeValue
   * @param <T>   the output's generic type
   * @return an unwrapped value
   */
  public static <T> T unwrap(Object value) {
    if (value instanceof TypedValue) {
      return ((TypedValue<T>) value).getValue();
    }

    return (T) value;
  }

  /**
   * Creates a new instance for the given {@code value}, using an auto calculated
   * {@link DataType}. This method is useful when no particular media types or
   * encoding are required
   *
   * @param value this object's content
   * @param <T>   the value's generic type
   * @return a new {@link TypedValue}
   */
  public static <T> TypedValue<T> of(T value) {
    if (value instanceof TypedValue) {
      return (TypedValue<T>) value;
    }

    return new TypedValue<>(value, DataType.fromObject(value));
  }

  private final T value;
  private final DataType dataType;

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value    this object's content.
   * @param dataType the {@link DataType} for this object's content.
   */
  public TypedValue(T value, DataType dataType) {
    this.value = value;
    if (dataType == null) {
      this.dataType = fromObject(value);
    } else {
      this.dataType = dataType;
    }
  }

  /**
   * Returns the data type (if any) associated with this {@link TypedValue}'s content.
   *
   * @return the {@link DataType} for this object's content.
   */
  public DataType getDataType() {
    return dataType;
  }

  /**
   * @return this object's content.
   */
  public T getValue() {
    return value;
  }

}
