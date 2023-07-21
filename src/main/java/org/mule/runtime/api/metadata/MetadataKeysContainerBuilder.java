/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Builder for {@link MetadataKeysContainer}
 * 
 * @since 1.0
 */
public final class MetadataKeysContainerBuilder {

  private Map<String, Set<MetadataKey>> keyMap = new HashMap<>();

  private MetadataKeysContainerBuilder() {}

  public static MetadataKeysContainerBuilder getInstance() {
    return new MetadataKeysContainerBuilder();
  }

  /**
   * @param name {@link Class#getSimpleName()} or alias of the {@link TypeKeysResolver} class
   * @param keys {@link Set<MetadataKey>} associated to the category.
   * @return {@code} this builder
   */
  public MetadataKeysContainerBuilder add(String name, Set<MetadataKey> keys) {
    keyMap.put(name, keys);
    return this;
  }

  /**
   * @param keys a {@link Map} which keys are categories and values are {@link MetadataKey}s associated to them
   * @return {@code} this builder
   */
  public MetadataKeysContainerBuilder addAll(Map<String, Set<MetadataKey>> keys) {
    keyMap.putAll(keys);
    return this;
  }

  /**
   * @return {@code true} if the category was already added
   */
  public boolean containsCategory(String categoryName) {
    return keyMap.containsKey(categoryName);
  }

  /**
   * @return {@link DefaultMetadataKeysContainer} with the built keys.
   */
  public MetadataKeysContainer build() {
    return new DefaultMetadataKeysContainer(keyMap);
  }
}
