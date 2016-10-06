/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.metadata.resolving.MetadataResult;

import java.util.List;

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
   * @return a {@link List} of {@link MetadataResult} of {@link ParameterMetadataDescriptor}
   * containing one result for each parameter that the operation has.
   */
  MetadataResult<InputMetadataDescriptor> getInputMetadata();

  /**
   * @return a {@link MetadataResult} with the {@link OutputMetadataDescriptor} of the Operation's output
   */
  MetadataResult<OutputMetadataDescriptor> getOutputMetadata();
}
