/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the view of all the Metadata associated to an Component's.
 * <p>
 * By default, only dynamic metadata is available. Static metadata can be made available by calling
 * {@link ComponentMetadataTypesDescriptorBuilder#keepNonDynamicMetadata}.
 *
 * @since 1.4
 */
public class ComponentMetadataTypesDescriptor {

  private final Map<String, MetadataType> inputMetadata;
  private final MessageMetadataType inputChainMetadata;
  private final MetadataType outputMetadata;
  private final MetadataType outputAttributesMetadata;

  public ComponentMetadataTypesDescriptor(Map<String, MetadataType> inputMetadata, MetadataType outputMetadata,
                                          MetadataType outputAttributesMetadata) {
    this(inputMetadata, null, outputMetadata, outputAttributesMetadata);
  }

  public ComponentMetadataTypesDescriptor(Map<String, MetadataType> inputMetadata,
                                          MessageMetadataType inputChainMetadata,
                                          MetadataType outputMetadata,
                                          MetadataType outputAttributesMetadata) {
    this.inputMetadata = inputMetadata;
    this.inputChainMetadata = inputChainMetadata;
    this.outputMetadata = outputMetadata;
    this.outputAttributesMetadata = outputAttributesMetadata;
  }

  public Map<String, MetadataType> getInputMetadata() {
    return new HashMap<>(inputMetadata);
  }

  public Optional<MessageMetadataType> getInputChainMetadata() {
    return ofNullable(inputChainMetadata);
  }

  public Optional<MetadataType> getInputMetadata(String parameterName) {
    return ofNullable(inputMetadata.getOrDefault(parameterName, null));
  }

  public Optional<MetadataType> getOutputMetadata() {
    return ofNullable(outputMetadata);
  }

  public Optional<MetadataType> getOutputAttributesMetadata() {
    return ofNullable(outputAttributesMetadata);
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

    private BaseInputMetadataDescriptor inputMetadataDescriptor;
    private OutputMetadataDescriptor outputMetadataDescriptor;
    private boolean keepNonDynamicMetadata = false;

    /**
     * Creates a new instance of {@link ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder}.
     */
    private ComponentMetadataTypesDescriptorBuilder() {}

    public ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder withInputMetadataDescriptor(InputMetadataDescriptor inputMetadataDescriptor) {
      return this.withInputMetadataDescriptor((BaseInputMetadataDescriptor) inputMetadataDescriptor);
    }

    public ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder withInputMetadataDescriptor(BaseInputMetadataDescriptor inputMetadataDescriptor) {
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
     * Allows to view not only the dynamic metadata of a component, but static metadata as well.
     * 
     * @param keepNonDynamicMetadata {@code true} if static metadata must be kept, {@code false} if only dynamic metadata is kept.
     *                               Default value is {@code false}.
     * @return this builder
     * 
     * @since 1.5
     */
    public ComponentMetadataTypesDescriptor.ComponentMetadataTypesDescriptorBuilder keepNonDynamicMetadata(boolean keepNonDynamicMetadata) {
      this.keepNonDynamicMetadata = keepNonDynamicMetadata;
      return this;
    }

    /**
     * @return a {@link ComponentMetadataTypesDescriptor} instance with the metadata description for the content, output, and type
     *         of each of the parameters of the Component
     */
    public ComponentMetadataTypesDescriptor build() {
      if (inputMetadataDescriptor == null && outputMetadataDescriptor == null) {
        throw new IllegalArgumentException("Input or output metadata descriptor has to be defined");
      }

      Map<String, MetadataType> input = emptyMap();
      if (inputMetadataDescriptor != null) {
        input = inputMetadataDescriptor.getAllParameters().values()
            .stream()
            .filter(keepNonDynamicMetadata
                ? p -> true
                : ParameterMetadataDescriptor::isDynamic)
            .collect(toMap(ParameterMetadataDescriptor::getName, ParameterMetadataDescriptor::getType));
      }

      MessageMetadataType chainInputMessageType = null;
      if (inputMetadataDescriptor instanceof ScopeInputMetadataDescriptor) {
        chainInputMessageType = ((ScopeInputMetadataDescriptor) inputMetadataDescriptor).getChainInputMessageType();
      }

      MetadataType output = null;
      MetadataType outputAttributes = null;
      if (outputMetadataDescriptor != null) {
        TypeMetadataDescriptor payloadMetadata = outputMetadataDescriptor.getPayloadMetadata();
        if (payloadMetadata.isDynamic() || keepNonDynamicMetadata) {
          output = payloadMetadata.getType();
        }

        TypeMetadataDescriptor attributesMetadata = outputMetadataDescriptor.getAttributesMetadata();
        if (attributesMetadata.isDynamic() || keepNonDynamicMetadata) {
          outputAttributes = attributesMetadata.getType();
        }
      }

      return new ComponentMetadataTypesDescriptor(input, chainInputMessageType, output, outputAttributes);
    }

  }
}
