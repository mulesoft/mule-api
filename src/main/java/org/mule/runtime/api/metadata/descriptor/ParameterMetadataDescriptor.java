/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static org.apache.commons.lang3.StringUtils.isBlank;
import org.mule.metadata.api.model.MetadataType;

/**
 * Immutable concrete implementation of a {@link TypeMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ParameterMetadataDescriptor {

  private final String name;
  private final MetadataType type;
  private final boolean isDynamic;

  private ParameterMetadataDescriptor(String name, MetadataType type, boolean isDynamic) {
    this.name = name;
    this.type = type;
    this.isDynamic = isDynamic;
  }

  public static ParameterMetadataDescriptorBuilder builder(String name) {
    return new ParameterMetadataDescriptorBuilder(name);
  }

  /**
   * @return parameter's name
   */
  public String getName() {
    return name;
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
  public static class ParameterMetadataDescriptorBuilder {

    private final String name;
    private MetadataType type;
    private boolean isDynamic = false;

    /**
     * Creates a new instance of the builder associated to the component parameter {@param name}
     *
     * @param name of a component parameter
     * @throws IllegalArgumentException if name is blank
     */
    private ParameterMetadataDescriptorBuilder(String name) {
      if (isBlank(name)) {
        throw new IllegalArgumentException("Name parameter cannot be blank for ParameterMetadataDescriptor");
      }

      this.name = name;
    }

    /**
     * Indicates that the component parameter with name {@link #name} will be of type {@param type}
     *
     * @param type of the component parameter
     * @return the instance builder contributed with a {@link MetadataType}
     */
    public ParameterMetadataDescriptorBuilder withType(MetadataType type) {
      this.type = type;
      return this;
    }

    /**
     * Indicates that the type comes from a dynamic type resolver
     *
     * @param isDynamic whether or not the provided type comes from a dynamic type resolver
     * @return the instance builder contributed with a {@link MetadataType}
     */
    public ParameterMetadataDescriptorBuilder dynamic(boolean isDynamic) {
      this.isDynamic = isDynamic;
      return this;
    }

    /**
     * Builds and creates the descriptor of the component parameter. Validates that the name nor type are not null.
     *
     * @return a {@link TypeMetadataDescriptor} with the metadata description of the component parameter
     * @throws IllegalArgumentException if type was not set during building
     */
    public ParameterMetadataDescriptor build() {
      if (type == null) {
        throw new IllegalArgumentException("Type parameter cannot be null for ParameterMetadataDescriptor");
      }

      return new ParameterMetadataDescriptor(name, type, isDynamic);
    }

  }

}
