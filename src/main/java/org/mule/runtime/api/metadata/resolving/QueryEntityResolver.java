/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataResolvingException;

import java.util.Set;

/**
 * Handles the dynamic resolution of all the available entities {@link MetadataKey}s when building a DSQL query and the dynamic
 * {@link MetadataType} resolution each of those entities.
 *
 * @since 1.0
 */
public interface QueryEntityResolver extends NamedTypeResolver {

  /**
   * {@inheritDoc}
   */
  @Override
  default String getResolverName() {
    return MetadataComponent.ENTITY.name();
  }

  /**
   * Resolves the {@link Set} of entities that can be queried in the DSQL operation that uses {@code this} resolver, representing
   * them as a {@link Set} of {@link MetadataKey}.
   *
   * @param context {@link MetadataContext} of the Metadata resolution
   * @return A set with all the {@link MetadataKey} representing all the available entities that can be queried.
   * @throws MetadataResolvingException if an error occurs during the {@link MetadataKey} building. See {@link FailureCode} for
   *                                    possible {@link MetadataResolvingException} reasons
   * @throws ConnectionException        if an error occurs when using the connection provided by the {@link MetadataContext}
   */
  Set<MetadataKey> getEntityKeys(MetadataContext context) throws MetadataResolvingException, ConnectionException;

  /**
   * Given a {@link String} representing the id of one of the entities, resolves the corresponding entity {@link MetadataType},
   *
   * @param context {@link MetadataContext} of the Metadata resolution
   * @param key     a {@link String} representing the type which's structure has to be resolved
   * @return {@link MetadataType} from the given {@param key}
   * @throws MetadataResolvingException if an error occurs during the {@link MetadataType} building. See {@link FailureCode} for
   *                                    possible {@link MetadataResolvingException} reasons
   * @throws ConnectionException        if an error occurs when using the connection provided by the {@link MetadataContext}
   */
  MetadataType getEntityMetadata(MetadataContext context, String key) throws MetadataResolvingException, ConnectionException;
}
