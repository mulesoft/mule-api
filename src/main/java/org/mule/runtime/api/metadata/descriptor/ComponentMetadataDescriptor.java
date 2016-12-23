/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

/**
 * Represents the view of all Metadata associated to an Operation
 *
 * @since 1.0
 */
public interface ComponentMetadataDescriptor {

  /**
   * @return the Operation whose metadata is described
   */
  String getName();

  /**
   * @return a {@link InputMetadataDescriptor} that describes the input metadata for all the input parameters.
   */
  InputMetadataDescriptor getInputMetadata();

  /**
   * @return a {@link OutputMetadataDescriptor} describing the Operation's output.
   */
  OutputMetadataDescriptor getOutputMetadata();
}
