/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.nio.charset.Charset.defaultCharset;
import static org.mule.runtime.api.metadata.DataType.fromObject;

import org.mule.runtime.api.util.LazyLong;
import org.mule.runtime.internal.util.StringByteSizeCalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * Maintains a value that has an associated {@link DataType}.
 *
 * @param <T> the content type.
 * @since 1.0
 */
public final class TypedValue<T> implements Serializable {

  private static final long serialVersionUID = -3428994331968741687L;

  /**
   * Utility method to obtain a type value's content, in cases in which you don't know if the {@code value} is a type value at
   * all.
   * <p>
   * If {@code value} is a TypeValue, then {@link #getValue()} is returned. Otherwise, the {@code value} is returned as is (even
   * if it's {@code null})
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
   * Creates a new instance for the given {@code value}, using an auto calculated {@link DataType}. This method is useful when no
   * particular media types or encoding are required
   *
   * @param value this object's content
   * @param <T>   the value's generic type
   * @return a new {@link TypedValue}
   */
  public static <T> TypedValue<T> of(T value) {
    if (value instanceof TypedValue) {
      return (TypedValue<T>) value;
    }

    return new TypedValue<>(value, value != null ? DataType.fromObject(value) : DataType.OBJECT);
  }

  private final T value;
  private final DataType dataType;
  private transient LazyLong length;

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value    this object's content.
   * @param dataType the {@link DataType} for this object's content.
   */
  public TypedValue(T value, DataType dataType) {
    this(value, dataType, OptionalLong.empty());
  }

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value    this object's content.
   * @param dataType the {@link DataType} for this object's content.
   * @param length   the length of the value in bytes.
   * @deprecated Use {@link #TypedValue(Object, DataType, OptionalLong)} instead.
   */
  @Deprecated
  public TypedValue(T value, DataType dataType, Optional<Long> length) {
    this(value, dataType, length.map(l -> OptionalLong.of(l)).orElse(OptionalLong.empty()));
  }

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   *
   * @param value    this object's content.
   * @param dataType the {@link DataType} for this object's content.
   * @param length   the length of the value in bytes.
   */
  public TypedValue(T value, DataType dataType, OptionalLong length) {
    this.value = value;
    if (dataType == null) {
      this.dataType = fromObject(value);
    } else {
      this.dataType = dataType;
    }
    if (length.isPresent()) {
      this.length = new LazyLong(length.getAsLong());
    } else if (value instanceof byte[]) {
      this.length = new LazyLong(((byte[]) value).length);
    } else if (value instanceof String) {
      this.length = new LazyLong(() -> {
        StringByteSizeCalculator stringByteSizeCalculator = new StringByteSizeCalculator();
        Charset charset = this.dataType.getMediaType().getCharset().orElse(defaultCharset());
        return stringByteSizeCalculator.count((String) value, charset);
      });
    } else {
      this.length = new LazyLong(-1L);
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
   * @deprecated Use {@link #getByteLength()} instead.
   */
  @Deprecated
  public Optional<Long> getLength() {
    long len = length.getAsLong();
    return len >= 0 ? Optional.of(len) : Optional.empty();
  }

  /**
   * If available obtain the length (in bytes) of the valye.
   *
   * @return length of the value in bytes.
   */
  public OptionalLong getByteLength() {
    long len = length.getAsLong();
    return len >= 0 ? OptionalLong.of(len) : OptionalLong.empty();
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

    if (length.getAsLong() != that.length.getAsLong()) {
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
    long len = length.getAsLong();
    result = 31 * result + (int) (len ^ (len >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "TypedValue[value: '" + value + "', dataType: '" + dataType + "']";
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    in.defaultReadObject();
    length = new LazyLong(in.readLong());
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    out.writeLong(length.getAsLong());
  }
}
