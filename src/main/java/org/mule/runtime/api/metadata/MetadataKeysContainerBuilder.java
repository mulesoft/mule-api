/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Builder for {@link MetadataKeysContainer}
 * 
 * @since 1.0
 */
public final class MetadataKeysContainerBuilder {

  private Map<String, Set<MetadataKey>> keyMap = new HashMap();

  /**
   * @param name {@link Class#getSimpleName()} or alias of the
   *        {@link org.mule.runtime.api.metadata.resolving.MetadataKeysResolver} class
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
   * @return {@link MetadataKeysContainer} with the built keys.
   * @throws {@link IllegalStateException} if a resolver {@link Set<MetadataKey>} is empty.
   */
  public MetadataKeysContainer build() {
    keyMap.entrySet().stream().filter(entry -> entry.getValue().isEmpty()).findFirst().ifPresent(entry -> {
      throw new IllegalStateException("Resolver [%s] has an empty key list");
    });

    return new MetadataKeysContainer(keyMap);
  }
}
