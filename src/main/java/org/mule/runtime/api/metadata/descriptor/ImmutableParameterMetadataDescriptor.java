/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
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
public final class ImmutableParameterMetadataDescriptor implements ParameterMetadataDescriptor {

  private final String name;
  private final MetadataType type;
  private final boolean isDynamic;

  public ImmutableParameterMetadataDescriptor(String name, MetadataType type, boolean isDynamic) {
    this.name = name;
    this.type = type;
    this.isDynamic = isDynamic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDynamic() {
    return isDynamic;
  }
}
