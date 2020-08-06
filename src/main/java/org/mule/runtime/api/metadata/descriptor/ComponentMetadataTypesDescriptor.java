/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import org.mule.metadata.api.model.MetadataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the view of all the dynamic Metadata associated to an Component's.
 *
 * @since 1.4
 */
public class ComponentMetadataTypesDescriptor {

  private Map<String, MetadataType> input;
  private MetadataType output;
  private MetadataType outputAttributes;

  public ComponentMetadataTypesDescriptor(Map<String, MetadataType> input, MetadataType output, MetadataType outputAttributes) {
    this.input = input;
    this.output = output;
    this.outputAttributes = outputAttributes;
  }

  public Map<String, MetadataType> getInputMetadata() {
    return new HashMap<>(input);
  }

  public Optional<MetadataType> getOutputMetadata() {
    return ofNullable(output);
  }

  public Optional<MetadataType> getOutputAttributesMetadata() {
    return ofNullable(outputAttributes);
  }

  public static ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder builder() {
    return new ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder();
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link ComponentMetadataTypesDescriptor}
   *
   * @since 1.4
   */
  public static class ComponentMetadataTypesDescriptorBuilder {

    private InputMetadataDescriptor inputMetadataDescriptor;
    private OutputMetadataDescriptor outputMetadataDescriptor;

    /**
     * Creates a new instance of {@link ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder}.
     */
    private ComponentMetadataTypesDescriptorBuilder() {}

    public ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder withInputMetadataDescriptor(InputMetadataDescriptor inputMetadataDescriptor) {
      requireNonNull(inputMetadataDescriptor, "inputMetadataDescriptor must not be null");
      this.inputMetadataDescriptor = inputMetadataDescriptor;
      return this;
    }

    public ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder withOutputMetadataDescriptor(OutputMetadataDescriptor outputMetadataDescriptor) {
      requireNonNull(outputMetadataDescriptor, "outputMetadataDescriptor must not be null");
      this.outputMetadataDescriptor = outputMetadataDescriptor;
      return this;
    }

    /**
     * @return a {@link ComponentMetadataTypesDescriptor} instance with the metadata description for the content, output, and type of
     * each of the parameters of the Component
     */
    public ComponentMetadataTypesDescriptor build() {
      if (inputMetadataDescriptor == null && outputMetadataDescriptor == null) {
        throw new IllegalArgumentException("Input or output metadata descriptor has to be defined");
      }

      Map<String, MetadataType> input = new HashMap<>();
      if (inputMetadataDescriptor != null) {
        inputMetadataDescriptor.getAllParameters().values().stream()
            .filter(ParameterMetadataDescriptor::isDynamic).forEach(p -> input.put(p.getName(), p.getType()));
      }

      MetadataType output = null;
      MetadataType outputAttributes = null;
      if (outputMetadataDescriptor != null) {
        TypeMetadataDescriptor payloadMetadata = outputMetadataDescriptor.getPayloadMetadata();

        if (payloadMetadata.isDynamic()) {
          output = payloadMetadata.getType();
        }

        TypeMetadataDescriptor attributesMetadata = outputMetadataDescriptor.getAttributesMetadata();
        if (attributesMetadata.isDynamic()) {
          outputAttributes = attributesMetadata.getType();
        }
      }

      return new ComponentMetadataTypesDescriptor(input, output, outputAttributes);
    }

  }
}
