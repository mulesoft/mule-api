/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;

import java.util.Set;

/**
 * Handles the dynamic resolution of the available {@link MetadataKey} that can be used to populate the MetadataKeyId of an
 * associated Component
 *
 * @since 1.0
 */
public interface TypeKeysResolver extends NamedTypeResolver {

  /**
   * Resolves the {@link Set} of types that can be described, representing them as a {@link Set} of {@link MetadataKey}
   *
   * @param context {@link MetadataContext} of the Metadata resolution
   * @return A set of {@link MetadataKey} of the available types
   * @throws MetadataResolvingException if an error occurs during the {@link MetadataKey} building. See {@link FailureCode} for
   *                                    possible {@link MetadataResolvingException} reasons
   * @throws ConnectionException        if an error occurs when using the connection provided by the {@link MetadataContext}
   */
  Set<MetadataKey> getKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException;

  /**
   * {@inheritDoc}
   */
  default String getResolverName() {
    return "keys";
  }
}
