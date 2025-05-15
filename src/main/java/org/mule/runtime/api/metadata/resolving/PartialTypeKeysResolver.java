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

/**
 * A {@link TypeKeysResolver} that can handle the resolution of multi-level {@link MetadataKey} in a lazy way. On each invocation,
 * this resolver will perform the resolution of one more level of the {@link MetadataKey} tree, based on the configuration of the
 * previous levels.
 *
 * @since 1.1
 */
public interface PartialTypeKeysResolver<T> extends TypeKeysResolver {

  /**
   * Further resolves the of types that can be described based on a partial configuration of the Type descriptor, representing
   * them as a multi level {@link MetadataKey}.
   *
   * For example, if we have a location key composed by:
   *
   * <pre>
   * {@code
   *  Location: {
   *    continent,
   *    country,
   *    city
   *  }
   * }
   * </pre>
   *
   * Were the {@code Continent} is required to resolve the {@code Country}, and the {@code Country} is required to resolve the
   * {@code City}, this {@link PartialTypeKeysResolver#resolveChilds} method will be invoked with the {@code Location}
   * configuration available so far. In this case, possible values for the {@code partial} parameter of this method are
   * {@code Location: { continent } } and {@code Location: { continent, country } }
   *
   * @param context {@link MetadataContext} of the Metadata resolution
   * @param partial the current, partial representation of the type key.
   * @return A multi level {@link MetadataKey} of representing the available types
   * @throws MetadataResolvingException if an error occurs during the {@link MetadataKey} building. See {@link FailureCode} for
   *                                    possible {@link MetadataResolvingException} reasons
   * @throws ConnectionException        if an error occurs when using the connection provided by the {@link MetadataContext}
   */
  MetadataKey resolveChilds(MetadataContext context, T partial) throws MetadataResolvingException, ConnectionException;

}
