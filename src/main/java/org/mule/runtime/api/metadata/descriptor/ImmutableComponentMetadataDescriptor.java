/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.metadata.resolving.MetadataResult;

import java.util.List;
import java.util.Optional;

/**
 * Immutable concrete implementation of {@link ComponentMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ImmutableComponentMetadataDescriptor implements ComponentMetadataDescriptor {

  private final String componentName;
  private final List<MetadataResult<ParameterMetadataDescriptor>> parameters;
  private final MetadataResult<OutputMetadataDescriptor> output;
  private final MetadataResult<ParameterMetadataDescriptor> content;

  public ImmutableComponentMetadataDescriptor(String componentName,
                                              List<MetadataResult<ParameterMetadataDescriptor>> parameters,
                                              MetadataResult<OutputMetadataDescriptor> output,
                                              MetadataResult<ParameterMetadataDescriptor> content) {
    this.componentName = componentName;
    this.parameters = parameters;
    this.output = output;
    this.content = content;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MetadataResult<ParameterMetadataDescriptor>> getParametersMetadata() {
    return this.parameters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<MetadataResult<ParameterMetadataDescriptor>> getContentMetadata() {
    return Optional.ofNullable(content);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataResult<OutputMetadataDescriptor> getOutputMetadata() {
    return this.output;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return componentName;
  }
}
