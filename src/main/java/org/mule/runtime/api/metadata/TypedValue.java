/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Optional.empty;
import static org.mule.runtime.api.metadata.DataType.fromObject;

import java.io.Serializable;
import java.util.Optional;

/**
 * Maintains a value that has an associated {@link DataType}.
 *
 * @param <T> the content type.
 * @since 1.0
 */
public final class TypedValue<T> implements Serializable {

  private static final long serialVersionUID = -2533879516750283994L;

  /**
   * Utility method to obtain a type value's content, in cases in which you don't know if the {@ccode value} is a type value at
   * all.
   * <p>
   * If {@code value} is a TypeValue, then {@link #getValue()} is returned. Otherwise, the {@code value} is returned as is (even
   * if it's {@code null})
   *
   * @param value a value which may or may not be a TypeValue
   * @param <T> the output's generic type
   * @return an unwrapped value
   */
  public static <T> T unwrap(Object value) {
    if (value instanceof TypedValue) {
      return ((TypedValue<T>) value).getValue();
    }

    return (T) value;
  }

  /**
   * Creates a new instance for the given {@code value}, using an auto calculated {@link DataType}. This method is useful when no
   * particular media types or encoding are required
   *
   * @param value this object's content
   * @param <T> the value's generic type
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
  private final long length;

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value this object's content.
   * @param dataType the {@link DataType} for this object's content.
   */
  public TypedValue(T value, DataType dataType) {
    this(value, dataType, empty());
  }

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value this object's content.
   * @param dataType the {@link DataType} for this object's content.
   * @param length the length of the value in bytes.
   */
  public TypedValue(T value, DataType dataType, Optional<Long> length) {
    this.value = value;
    if (dataType == null) {
      this.dataType = fromObject(value);
    } else {
      this.dataType = dataType;
    }
    if (length.isPresent()) {
      this.length = length.get();
    } else if (value instanceof byte[]) {
      this.length = ((byte[]) value).length;
    } else if (value instanceof String) {
      this.length = ((String) value).getBytes(this.dataType.getMediaType().getCharset().orElse(defaultCharset())).length;
    } else {
      this.length = -1;
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

  /**
   * If available obtain the length (in bytes) of the valye.
   * 
   * @return length of the value in bytes.
   */
  public Optional<Long> getLength() {
    return length >= 0 ? Optional.of(length) : empty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TypedValue<?> that = (TypedValue<?>) o;

    if (length != that.length) {
      return false;
    }
    if (value != null ? !value.equals(that.value) : that.value != null) {
      return false;
    }
    return dataType.equals(that.dataType);
  }

  @Override
  public int hashCode() {
    int result = value != null ? value.hashCode() : 0;
    result = 31 * result + dataType.hashCode();
    result = 31 * result + (int) (length ^ (length >>> 32));
    return result;
  }
}
