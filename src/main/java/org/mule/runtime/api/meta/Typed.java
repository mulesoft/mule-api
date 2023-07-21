/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.MetadataService;

/**
 * A generic contract for a component that has a type.
 *
 * @since 1.0
 */
@NoImplement
public interface Typed {

  /**
   * Returns the {@link MetadataType} of the {@link Typed} component.
   *
   * @return a not {@code null} {@link MetadataType}
   */
  MetadataType getType();

  /**
   * Returns {@code true} if the type of the {@link Typed Component} is of dynamic kind, and has to be discovered during design
   * time using the {@link MetadataService} service.
   *
   * @return {@code true} if {@code this} element type is of dynamic kind
   */
  boolean hasDynamicType();

}
