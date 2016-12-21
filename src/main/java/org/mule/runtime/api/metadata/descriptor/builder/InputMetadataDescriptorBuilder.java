/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.MetadataProvider;
import org.mule.runtime.api.metadata.descriptor.ImmutableInputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.InputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ParameterMetadataDescriptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the builder design pattern to create instances of {@link OutputMetadataDescriptor}
 *
 * @since 1.0
 */
public class InputMetadataDescriptorBuilder {

  private Map<String, ParameterMetadataDescriptor> parameters = new HashMap<>();

  /**
   * Creates a new instance of {@link InputMetadataDescriptorBuilder}
   */
  InputMetadataDescriptorBuilder() {}

  /**
   * @param name         asdasda
   * @param parameterResult   asdasdasd complete
   * @return
   */
  public InputMetadataDescriptorBuilder withParameter(String name, ParameterMetadataDescriptor parameterResult) {
    if (parameterResult == null) {
      throw new IllegalArgumentException("A null ParameterMetadataDescriptor is not valid for the InputMetadataDescriptor");
    }
    parameters.put(name, parameterResult);
    return this;
  }

  /**
   * @return a {@link OutputMetadataDescriptor} instance with the metadata description for the output of a
   *         {@link MetadataProvider} component
   * @throws IllegalArgumentException if the {@link Message#getPayload} or {@link Message#getAttributes} were not set
   *         during building
   */
  public InputMetadataDescriptor build() {
    return new ImmutableInputMetadataDescriptor(parameters);
  }

}
