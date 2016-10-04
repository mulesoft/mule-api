/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

import java.util.Map;

/**
 * Immutable implementation of {@link InputMetadataDescriptor}
 *
 * @since 1.0
 */
public class ImmutableInputMetadataDescriptor implements InputMetadataDescriptor {

  private final Map<String, MetadataResult<ParameterMetadataDescriptor>> parameters;

  public ImmutableInputMetadataDescriptor(Map<String, MetadataResult<ParameterMetadataDescriptor>> parameters) {
    this.parameters = parameters;
  }

  @Override
  public MetadataResult<ParameterMetadataDescriptor> getParameterMetadata(String paramName) {
    MetadataResult<ParameterMetadataDescriptor> result = parameters.get(paramName);
    if (result == null) {
      throw new IllegalArgumentException(format("The parameter [%s] does not belong to the described component", paramName));
    }

    return result;
  }

  @Override
  public Map<String, MetadataResult<ParameterMetadataDescriptor>> getAllParameters() {
    return unmodifiableMap(parameters);
  }
}
