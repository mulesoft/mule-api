/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.metadata.resolving.MetadataKeysResolver;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * This interface allows a the expose the {@link MetadataKey}
 * information associated to its inner components which are associated to a {@link MetadataKeysResolver}.
 *
 * @since 1.0
 */
public interface MetadataKeysAware {

  /**
   * Returns the a {@link MetadataKeysContainer} with the {@link MetadataKey}s provided per
   * {@link MetadataKeysResolver} associated to this Component.
   *
   * @return Successful {@link MetadataResult} if the keys are successfully resolved Failure {@link MetadataResult} if there is an
   *         error while retrieving the keys
   * @throws MetadataResolvingException if an error occurs while creating the {@link MetadataContext}
   */

  MetadataResult<MetadataKeysContainer> getMetadataKeys() throws MetadataResolvingException;
}

