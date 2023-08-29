/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Container for the {@link MetadataKey}s obtained from a {@link MetadataKeyProvider} component.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataKeysContainer {

  /**
   * Returns an {@link Optional} with {@link Set<MetadataKey>} for a given resolver name if it is present.
   * {@link Optional#empty()} otherwise.
   *
   * @param categoryName of the {@link TypeKeysResolver}
   * @return {@link Optional} of {@link Set<MetadataKey>} associated to the resolver
   */
  Optional<Set<MetadataKey>> getKeys(String categoryName);

  /**
   * @return {@link Set} with the categories names
   */
  Set<String> getCategories();

  /**
   * @return an immutable {@link Map} with the names of the categories as keys and the {@link Set<MetadataKey>} for each category
   *         as the associated value.
   */
  Map<String, Set<MetadataKey>> getKeysByCategory();
}
