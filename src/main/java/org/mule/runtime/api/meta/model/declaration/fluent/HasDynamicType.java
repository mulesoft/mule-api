/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.metadata.MetadataService;
import org.mule.runtime.api.meta.model.ComponentModel;

/**
 * A contract interface for a declarer capable of adding a dynamic {@link MetadataType type} to the described Element
 *
 * @param <T> the type of the implementing type. Used to allow method chaining
 * @since 1.0
 */
interface HasDynamicType<T> {

  /**
   * Specifies that the Element being described has a {@link MetadataType type} of <b>dynamic</b> kind, meaning that this
   * Element's type is bounded to the {@link ComponentModel component}'s configuration during application design time and should
   * be discovered using the {@link MetadataService} service
   *
   * @param type the type of the Element being described
   * @return {@code this} declarer
   */
  T ofDynamicType(MetadataType type);
}
