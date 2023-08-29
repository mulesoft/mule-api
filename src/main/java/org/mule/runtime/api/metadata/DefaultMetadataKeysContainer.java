/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link MetadataKeysContainer}.
 * 
 * @since 1.0
 */
final class DefaultMetadataKeysContainer implements MetadataKeysContainer {

  private Map<String, Set<MetadataKey>> keyMap = new HashMap();

  DefaultMetadataKeysContainer(Map<String, Set<MetadataKey>> keyMap) {
    this.keyMap = unmodifiableMap(keyMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Set<MetadataKey>> getKeys(String categoryName) {
    Set<MetadataKey> keys = keyMap.get(categoryName);
    return keys == null ? Optional.empty() : Optional.of(unmodifiableSet(keys));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getCategories() {
    return unmodifiableSet(keyMap.keySet());
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Set<MetadataKey>> getKeysByCategory() {
    return keyMap;
  }
}
