/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.runtime.api.resolving.ExtensionResolvingContext;

/**
 * Metadata resolving context, provides access to the Config and Connection used during
 * metadata fetch invocation.
 *
 * @since 1.0
 */
public interface MetadataContext extends ExtensionResolvingContext {

  /**
   * @returns the {@link MetadataCache} associated with the {@link MetadataContext}.
   */
  MetadataCache getCache();

}
