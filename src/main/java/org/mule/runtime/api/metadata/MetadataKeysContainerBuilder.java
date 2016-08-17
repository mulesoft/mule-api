/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;

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
   * @param name {@link Class#getSimpleName()} or alias of the {@link MetadataKeysResolver} class
   * @param keys {@link Set<MetadataKey>} associated to ther esolver.
   */
  public MetadataKeysContainerBuilder add(String name, Set<MetadataKey> keys) {
    keyMap.put(name, keys);
    return this;
  }

  /**
   * @return {@code true} if the resolver was already added
   */
  public boolean containsResolver(String resolverName) {
    return keyMap.containsKey(resolverName);
  }

  /**
   * @return {@link DefaultMetadataKeysContainer} with the built keys.
   */
  public MetadataKeysContainer build() {
    return new DefaultMetadataKeysContainer(keyMap);
  }
}
