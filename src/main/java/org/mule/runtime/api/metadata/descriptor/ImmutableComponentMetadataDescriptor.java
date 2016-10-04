/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import org.mule.runtime.api.metadata.resolving.MetadataResult;

/**
 * Immutable concrete implementation of {@link ComponentMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ImmutableComponentMetadataDescriptor implements ComponentMetadataDescriptor {

  private final String name;
  private final MetadataResult<InputMetadataDescriptor> input;
  private final MetadataResult<OutputMetadataDescriptor> output;

  public ImmutableComponentMetadataDescriptor(String name, MetadataResult<InputMetadataDescriptor> input,
                                              MetadataResult<OutputMetadataDescriptor> output) {
    this.name = name;
    this.input = input;
    this.output = output;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataResult<InputMetadataDescriptor> getInputMetadata() {
    return this.input;
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
    return name;
  }
}
