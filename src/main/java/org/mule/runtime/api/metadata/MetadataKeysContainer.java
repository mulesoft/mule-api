/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Container for the {@link MetadataKey}s obtained from a {@link MetadataKeyProvider} component.
 * 
 * @since 1.0
 */
public final class MetadataKeysContainer {

  private Map<String, Set<MetadataKey>> keyMap = new HashMap();

  MetadataKeysContainer(Map<String, Set<MetadataKey>> keyMap) {
    this.keyMap = unmodifiableMap(keyMap);
  }

  /**
   * Returns an {@link Optional} wuth {@link Set<MetadataKey>} for a given resolver name if it is present.
   * {@link Optional#empty()} otherwise.
   * 
   * @param resolverName {@link Class#getSimpleName()} or alias of the {@link MetadataKeysResolver} class
   * @return {@link Optional} of {@link Set<MetadataKey>} associated to the resolver
   */
  public Optional<Set<MetadataKey>> getKeys(String resolverName) {
    if (!keyMap.containsKey(resolverName)) {
      return Optional.empty();
    }
    return Optional.of(unmodifiableSet(keyMap.get(resolverName)));
  }

  /**
   * @return {@link Set} with the {@link Class#getSimpleName()} or alias of the resolvers
   */
  public Set<String> getResolvers() {
    return unmodifiableSet(keyMap.keySet());
  }
}
