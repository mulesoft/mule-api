/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
