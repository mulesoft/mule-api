/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * A custom property which augments a {@link MetadataEnrichableModel} with
 * non canonical pieces of information.
 * <p>
 * Implementations of this interface must be immutable because if the
 * {@link MetadataProperty} change the after process could become inconsistent.
 *
 * @since 1.0
 */
public interface MetadataProperty {

  /**
   * A unique name which identifies this property.
   *
   * @return a unique name
   */
  String getName();

}
