/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;

/**
 * Handles the dynamic {@link MetadataType} resolving for the output attributes of an associated component.
 *
 * @param <K> the generic type of the objects to be used as MetadataKeyId representing a type structure
 * @since 1.0
 */
public interface AttributesTypeResolver<K> extends NamedTypeResolver {

  /**
   * {@inheritDoc}
   */
  @Override
  default String getResolverName() {
    return MetadataComponent.OUTPUT_ATTRIBUTES.name();
  }

  /**
   * Given an instance of type {@code K}, resolves their {@link MetadataType}, which represents the type structure. This
   * {@link MetadataType} will be considered as the resulting {@link Message} attributes type of the associated Component's output
   *
   * @param context {@link MetadataContext} of the MetaData resolution
   * @param key     {@code K} representing the type which's structure has to be resolved
   * @return {@link MetadataType} associated to the given {@param key}
   * @throws MetadataResolvingException if an error occurs during the {@link MetadataType} building. See {@link FailureCode} for
   *                                    possible {@link MetadataResolvingException} reasons
   * @throws ConnectionException        if an error occurs when using the connection provided by the {@link MetadataContext}
   */
  MetadataType getAttributesType(MetadataContext context, K key) throws MetadataResolvingException, ConnectionException;
}
