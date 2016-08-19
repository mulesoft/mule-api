/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

import java.util.Map;
import java.util.Set;

/**
 * This interface allows a configuration to expose the {@link MetadataKey} information associated to its inner components which
 * are associated to a {@link MetadataKeysResolver}.
 *
 * @since 1.0
 */
public interface MetadataKeyAware {

  /**
   * Returns a {@link Map} with the fully qualified names of the {@link MetadataKeysResolver} classes as keys, and a {@link Set}
   * of {@link MetadataKey} that are associated to each resolver.
   *
   * @return Successful {@link MetadataResult} if the keys of all the resolvers are successfully resolved Failure
   *         {@link MetadataResult} if there is an error while retrieving the keys
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   */
  MetadataResult<Map<String, Set<MetadataKey>>> getMetadataKeys() throws MetadataResolvingException;
}

