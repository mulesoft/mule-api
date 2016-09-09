/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Maintains a value that has an associated {@link DataType}.
 * 
 * @param <T> the content type.
 * @since 1.0
 */
public interface TypedValue<T> {

  /**
   * Returns the data type (if any) associated with this {@link TypedValue}'s content.
   *
   * @return the {@link DataType} for this object's content.
   */
  DataType getDataType();

  /**
   * @return this object's content.
   */
  T getValue();

}
