/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;

/**
 * Contract interface for a {@link BaseDeclaration} in which
 * it's possible to define the declared component's {@link MetadataType}
 *
 * @since 1.0
 */
public interface TypedDeclaration {

  /**
   * @return the declared {@link MetadataType} of the element
   */
  MetadataType getType();

  /**
   * Associates the given {@link MetadataType} to the element being declared
   *
   * @param type the element's static {@link MetadataType}
   * @param isDynamic whether or not the element being declare supports a dynamic resolution
   *                  of it's type, regardless of the {@code type} statically associated
   */
  void setType(MetadataType type, boolean isDynamic);

  /**
   * @return true if the element's type has support to be resolved dynamically
   */
  boolean hasDynamicType();

}
