/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.io.Serializable;

/**
 * Maintains a value that has an associated {@link DataType}.
 * 
 * @param <T> the content type.
 * @since 1.0
 */
public final class TypedValue<T> implements Serializable {

  private static final long serialVersionUID = -2533879516750283994L;

  private final T value;
  private final DataType dataType;

  /**
   * Constructs a new {@link TypedValue} with the given parameters.
   * 
   * @param value this object's content.
   * @param dataType the {@link DataType} for this object's content.
   */
  public TypedValue(T value, DataType dataType) {
    this.value = value;
    if (dataType == null) {
      this.dataType = DataType.fromObject(value);
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
