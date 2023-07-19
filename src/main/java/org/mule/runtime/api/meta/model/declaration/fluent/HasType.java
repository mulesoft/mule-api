/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;

/**
 * A contract interface for a declarer capable of adding an static {@link MetadataType type} to the described Element
 *
 * @param <T> the type of the implementing type. Used to allow method chaining
 * @since 1.0
 */
interface HasType<T> {

  /**
   * Specifies that the Element being described has a {@link MetadataType type} of <b>static</b> kind.
   *
   * @param type the type of the Element being described
   * @return {@code this} declarer
   */
  T ofType(MetadataType type);
}
