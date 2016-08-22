/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Container for the {@link MetadataKey}s obtained from a {@link MetadataKeysAware} component.
 * 
 * @since 1.0
 */
public final class MetadataKeysContainer {

  private Map<String, Set<MetadataKey>> keyMap = new HashMap();

  public MetadataKeysContainer(Map<String, Set<MetadataKey>> keyMap) {
    this.keyMap = keyMap;
  }

  /**
   * Returns a {@link Set<MetadataKey>} for a given resolver name
   * 
   * @param resolverName {@link Class#getSimpleName()} or alias of the {@link Metadata}
   * @return {@link Set<MetadataKey>} associated to the resolver
   */
  public Set<MetadataKey> getKeys(String resolverName) {
    return Collections.unmodifiableSet(keyMap.get(resolverName));
  }

  /**
   * Returns a {@link Set<MetadataKey>} if there was only one resolver present in the container.
   * 
   * @return {@link Set<MetadataKey>} for the only resolver present.
   * @throws {@link IllegalStateException} if there was more than one or no resolver at all.
   */
  public Set<MetadataKey> getKeys() {
    if (keyMap.size() == 1) {
      return Collections.unmodifiableSet(keyMap.get(keyMap.keySet().toArray()[0]));
    } else if (keyMap.size() > 1) {
      throw new IllegalStateException("Could not return keys from container because there was more than one resolver available.");
    }

    throw new IllegalStateException("Could not return keys from container because there was no resolver available.");
  }

  /**
   *
   * @return {@link Map} {@link Class#getSimpleName()} or alias of the resolver and its corresponding {@link MetadataKey}s.
   */
  public Map<String, Set<MetadataKey>> getAllKeys() {
    return Collections.unmodifiableMap(keyMap);
  }
}
