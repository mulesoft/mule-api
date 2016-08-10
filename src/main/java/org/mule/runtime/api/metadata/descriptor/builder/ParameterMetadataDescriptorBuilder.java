/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import org.mule.runtime.api.metadata.descriptor.ImmutableParameterMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ParameterMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.metadata.api.model.MetadataType;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of the builder design pattern to create instances of {@link TypeMetadataDescriptor}
 *
 * @since 1.0
 */
public class ParameterMetadataDescriptorBuilder {

  private final String name;
  private MetadataType type;

  /**
   * Creates a new instance of the builder associated to the component parameter {@param name}
   *
   * @param name of a component parameter
   * @throws IllegalArgumentException if name is blank
   */
  ParameterMetadataDescriptorBuilder(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Name parameter cannot be blank for TypeMetadataDescriptor");
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
   * Builds and creates the descriptor of the component parameter.
   * Validates that the name nor type are not null.
   *
   * @return a {@link TypeMetadataDescriptor} with the metadata description of the component parameter
   * @throws IllegalArgumentException if type was not set during building
   */
  public ParameterMetadataDescriptor build() {
    if (type == null) {
      throw new IllegalArgumentException("Type parameter cannot be null for ParameterMetadataDescriptor");
    }

    return new ImmutableParameterMetadataDescriptor(name, type);
  }

}
