/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Container for the {@link MetadataKey}s obtained from a {@link MetadataKeyProvider} component.
 *
 * @since 1.0
 */
public interface MetadataKeysContainer {

  /**
   * Returns an {@link Optional} with {@link Set<MetadataKey>} for a given resolver name if it is present.
   * {@link Optional#empty()} otherwise.
   *
   * @param resolverName {@link Class#getSimpleName()} or alias of the {@link MetadataKeysResolver} class
   * @return {@link Optional} of {@link Set<MetadataKey>} associated to the resolver
   */
  Optional<Set<MetadataKey>> getKeys(String resolverName);

  /**
   * @return {@link Set} with the {@link Class#getSimpleName()} or alias of the resolvers
   */
  Set<String> getResolvers();

  /**
   * @return {@link Map} with the resolver's name as key and the {@link Set<MetadataKey>} for that resolver as value.
   */
  Map<String, Set<MetadataKey>> getKeys();
}
