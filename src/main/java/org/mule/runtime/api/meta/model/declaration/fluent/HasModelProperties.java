/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ModelProperty;

/**
 * A contract interface for a declarer capable of adding a model properties
 *
 * @param <T> the type of the implementing type. Used to allow method chaining
 * @since 1.0
 */
@NoImplement
public interface HasModelProperties<T> {

  /**
   * Adds the given {@code modelProperty}
   *
   * @param modelProperty a {@link ModelProperty}
   * @return {@code this} declarer
   * @throws IllegalArgumentException if {@code modelProperty} is {@code null{}}
   */
  T withModelProperty(ModelProperty modelProperty);

}
