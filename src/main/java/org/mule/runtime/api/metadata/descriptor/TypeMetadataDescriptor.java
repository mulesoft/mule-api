/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.metadata.api.model.MetadataType;

/**
 * Immutable concrete implementation of a {@link TypeMetadataDescriptor}
 *
 * @since 1.0
 */
public class TypeMetadataDescriptor {

  private final MetadataType type;
  private final boolean isDynamic;


  private TypeMetadataDescriptor(MetadataType type, boolean isDynamic) {
    this.type = type;
    this.isDynamic = isDynamic;
  }

  public static TypeMetadataDescriptorBuilder builder() {
    return new TypeMetadataDescriptorBuilder();
  }

  /**
   * @return the component's {@link MetadataType}
   */
  public MetadataType getType() {
    return type;
  }

  /**
   * @return true if the {@link MetadataType} was provided by a dynamic type resolver
   */
  public boolean isDynamic() {
    return isDynamic;
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link TypeMetadataDescriptor}
   *
   * @since 1.0
   */
  public static class TypeMetadataDescriptorBuilder {

    private MetadataType type;
    private boolean isDynamic = false;

    /**
     * Creates a new instance of the builder.
     */
    private TypeMetadataDescriptorBuilder() {}

    /**
     * Indicates that the component will be of type {@param type}
     *
     * @param type of the component parameter
     * @return the instance builder contributed with a {@link MetadataType}
     */
    public TypeMetadataDescriptorBuilder withType(MetadataType type) {
      this.type = type;
      return this;
    }

    /**
     * Indicates that the type comes from a dynamic type resolver
     *
     * @param isDynamic whether or not the provided type comes from a dynamic type resolver
     * @return the instance builder contributed with a {@link MetadataType}
     */
    public TypeMetadataDescriptorBuilder dynamic(boolean isDynamic) {
      this.isDynamic = isDynamic;
      return this;
    }

    /**
     * Builds and creates the descriptor of the component.
     * <p>
     * Validates that the type is not null.
     *
     * @return a {@link TypeMetadataDescriptor} with the metadata description of the component.
     * @throws IllegalArgumentException if type was not set during building
     */
    public TypeMetadataDescriptor build() {
      if (type == null) {
        throw new IllegalArgumentException("Type parameter cannot be null for ParameterMetadataDescriptor");
      }

      return new TypeMetadataDescriptor(type, isDynamic);
    }

  }

}
