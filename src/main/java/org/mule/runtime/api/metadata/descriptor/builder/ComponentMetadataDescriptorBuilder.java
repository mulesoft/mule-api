/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor.builder;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.metadata.descriptor.builder.MetadataDescriptorBuilder.inputDescriptor;
import static org.mule.runtime.api.metadata.resolving.MetadataResult.success;
import org.mule.runtime.api.metadata.descriptor.ComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ImmutableComponentMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ImmutableInputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.InputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.OutputMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.ParameterMetadataDescriptor;
import org.mule.runtime.api.metadata.descriptor.TypeMetadataDescriptor;
import org.mule.runtime.api.metadata.resolving.MetadataResult;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of the builder design pattern to create instances of {@link ComponentMetadataDescriptor}
 *
 * @since 1.0
 */
public class ComponentMetadataDescriptorBuilder {

  private final String name;
  private MetadataResult<InputMetadataDescriptor> input;
  private MetadataResult<OutputMetadataDescriptor> output;

  /**
   * Creates a new instance of {@link ComponentMetadataDescriptorBuilder} with the name of the component to describe
   *
   * @param name of the Component to describe its metadata. This name can not be blank
   * @throws IllegalArgumentException if name is blank
   */
  ComponentMetadataDescriptorBuilder(String name) {
    if (isBlank(name)) {
      throw new IllegalArgumentException("Name parameter cannot be blank for an ComponentMetadataDescriptor");
    }

    this.name = name;
  }

  /**
   * @param input a {@link MetadataResult} of {@link List} of {@link TypeMetadataDescriptor} that describe the metadata for each parameter
   *                   of the component described.
   * @return the contributed descriptor builder
   */
  public ComponentMetadataDescriptorBuilder withInputDescriptor(MetadataResult<InputMetadataDescriptor> input) {
    this.input = input;
    return this;
  }

  /**
   * @param output a {@link MetadataResult} of {@link OutputMetadataDescriptor} that describes the output of the component
   * @return the contributed descriptor builder contributed with the {@link OutputMetadataDescriptor}
   */
  public ComponentMetadataDescriptorBuilder withOutputDescriptor(MetadataResult<OutputMetadataDescriptor> output) {
    this.output = output;
    return this;
  }

  /**
   * @return a {@link ComponentMetadataDescriptor} instance with the metadata description for the content, output, and
   * type of each of the parameters of the Component
   * @throws IllegalArgumentException if the {@link OutputMetadataDescriptor} was not set during building
   */
  public ComponentMetadataDescriptor build() {
    if (output == null) {
      throw new IllegalArgumentException("OutputMetadataDescriptor cannot be null in an ComponentMetadataDescriptor");
    }

    if (input == null) {
      input = success(inputDescriptor().build());
    }

    return new ImmutableComponentMetadataDescriptor(name, input, output);
  }
}
