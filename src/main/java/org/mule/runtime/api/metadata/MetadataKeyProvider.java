/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.metadata.resolving.MetadataResult;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

/**
 * This interface allows the exposure of the {@link MetadataKey}s associated to the {@link TypeKeysResolver} of the component.
 *
 * @since 1.0
 */
@NoImplement
public interface MetadataKeyProvider {

  /**
   * Returns the a {@link MetadataKeysContainer} with the {@link MetadataKey}s provided per {@link TypeKeysResolver} associated to
   * this Component.
   *
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while retrieving the keys
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   */
  MetadataResult<MetadataKeysContainer> getMetadataKeys() throws MetadataResolvingException;

  /**
   * Returns the a {@link MetadataKeysContainer} with the {@link MetadataKey}s provided per {@link TypeKeysResolver} associated to
   * this Component.
   *
   * @param partialKey {@link MetadataKey} to be resolved.
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while retrieving the keys
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   * @since 1.4
   */
  MetadataResult<MetadataKeysContainer> getMetadataKeys(MetadataKey partialKey) throws MetadataResolvingException;

}

