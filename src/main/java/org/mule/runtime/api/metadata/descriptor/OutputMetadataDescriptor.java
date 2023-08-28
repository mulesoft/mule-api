/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.MetadataProvider;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * Immutable implementation of {@link OutputMetadataDescriptor}
 *
 * @since 1.0
 */
public final class OutputMetadataDescriptor {

  private final TypeMetadataDescriptor content;
  private final TypeMetadataDescriptor attributes;

  private OutputMetadataDescriptor(TypeMetadataDescriptor content,
                                   TypeMetadataDescriptor attributes) {
    this.content = content;
    this.attributes = attributes;
  }

  public static OutputMetadataDescriptorBuilder builder() {
    return new OutputMetadataDescriptorBuilder();
  }

  /**
   * @return a {@link TypeMetadataDescriptor} that describes the Component's output {@link Message#getPayload}
   */
  public TypeMetadataDescriptor getPayloadMetadata() {
    return content;
  }

  /**
   * @return a {@link TypeMetadataDescriptor} that describes Component's output {@link Message#getAttributes}
   */
  public TypeMetadataDescriptor getAttributesMetadata() {
    return attributes;
  }

  public static class OutputMetadataDescriptorBuilder {

    private TypeMetadataDescriptor returnTypeResult;
    private TypeMetadataDescriptor attributes;

    /**
     * Creates a new instance of {@link OutputMetadataDescriptorBuilder}
     */
    private OutputMetadataDescriptorBuilder() {}

    /**
     * Adds a {@link MetadataResult} of {@link TypeMetadataDescriptor} {@param returnTypeResult} that describes the return type of
     * the component.
     *
     * @param returnTypeResult a {@link MetadataResult} of {@link TypeMetadataDescriptor} describing the component output return
     *                         type
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for return type
     */
    public OutputMetadataDescriptorBuilder withReturnType(TypeMetadataDescriptor returnTypeResult) {
      this.returnTypeResult = returnTypeResult;
      return this;
    }

    /**
     * Adds a {@link MetadataResult} of {@link TypeMetadataDescriptor} {@param returnTypeResult} that describes the he output
     * {@link Message#getAttributes} {@link MetadataType}.
     *
     * @param attributesTypeResult a {@link MetadataResult} of {@link TypeMetadataDescriptor} describing the component output
     *                             attributes type.
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for message attributes
     */
    public OutputMetadataDescriptorBuilder withAttributesType(TypeMetadataDescriptor attributesTypeResult) {
      this.attributes = attributesTypeResult;
      return this;
    }

    /**
     * Describes that the return type of the component will be of {@link MetadataType} {@param returnTypeResult}
     *
     * @param returnType of the component returnTypeResult output
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for return type
     */
    public OutputMetadataDescriptorBuilder withReturnType(MetadataType returnType) {
      this.returnTypeResult = TypeMetadataDescriptor.builder().withType(returnType).build();
      return this;
    }

    /**
     * Describes that the output {@link Message#getAttributes} {@link MetadataType} of the component will be
     * {@param attributesTypeResult}
     *
     * @param attributesType of the component output attributes
     * @return the builder instance enriched with the {@link TypeMetadataDescriptor} for message attributes
     */
    public OutputMetadataDescriptorBuilder withAttributesType(MetadataType attributesType) {
      this.attributes = TypeMetadataDescriptor.builder().withType(attributesType).build();
      return this;
    }

    /**
     * @return a {@link OutputMetadataDescriptor} instance with the metadata description for the output of a
     *         {@link MetadataProvider} component
     * @throws IllegalArgumentException if the {@link Message#getPayload} or {@link Message#getAttributes} were not set during
     *                                  building
     */
    public OutputMetadataDescriptor build() {
      if (returnTypeResult == null) {
        throw new IllegalArgumentException("Payload type parameter cannot be null for OutputMetadataDescriptor");
      }
      if (attributes == null) {
        throw new IllegalArgumentException("Attributes type parameter cannot be null for OutputMetadataDescriptor");
      }

      return new OutputMetadataDescriptor(returnTypeResult, attributes);
    }

  }

}
