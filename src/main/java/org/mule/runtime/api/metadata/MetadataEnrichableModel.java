/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;
import java.util.Set;

/**
 * A model which can be augmented with {@link MetadataProperty MetadataProperties} that are not part of the canonical metadata
 * model.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataEnrichableModel {

  /**
   * Returns a registered model property of type{@code propertyType}
   *
   * @param propertyType the {@link Class} of the {@link MetadataProperty} which is being queried
   * @param <T>          the generic type of the return value
   * @return an {@link Optional} {@link MetadataProperty}
   */
  <T extends MetadataProperty> Optional<T> getMetadataProperty(Class<T> propertyType);

  /**
   * @return the entire {@link Set} of {@link MetadataProperty}s of the current element.
   */
  Set<MetadataProperty> getProperties();
}
