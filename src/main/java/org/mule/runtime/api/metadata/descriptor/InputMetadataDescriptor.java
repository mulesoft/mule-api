/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.MetadataProvider;

import java.util.Map;

/**
 * Represents the view of all the Metadata associated to a Component's {@link Message} output
 *
 * @since 1.0
 */
public final class InputMetadataDescriptor extends BaseInputMetadataDescriptor {

  private InputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters) {
    super(parameters);
  }

  public static InputMetadataDescriptorBuilder builder() {
    return new InputMetadataDescriptorBuilder();
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link OutputMetadataDescriptor}
   *
   * @since 1.0
   */
  public static final class InputMetadataDescriptorBuilder
      extends BaseInputMetadataDescriptorBuilder<InputMetadataDescriptor, InputMetadataDescriptorBuilder> {

    /**
     * Creates a new instance of {@link InputMetadataDescriptorBuilder}
     */
    protected InputMetadataDescriptorBuilder() {}

    /**
     * @return a {@link OutputMetadataDescriptor} instance with the metadata description for the output of a
     *         {@link MetadataProvider} component
     * @throws IllegalArgumentException if the {@link Message#getPayload} or {@link Message#getAttributes} were not set during
     *                                  building
     */
    @Override
    public InputMetadataDescriptor build() {
      return new InputMetadataDescriptor(parameters);
    }
  }
}
