/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

/**
 * Immutable implementation of {@link OutputMetadataDescriptor}
 *
 * @since 1.0
 */
public final class ImmutableOutputMetadataDescriptor implements OutputMetadataDescriptor {

  private final TypeMetadataDescriptor content;
  private final TypeMetadataDescriptor attributes;

  public ImmutableOutputMetadataDescriptor(TypeMetadataDescriptor content,
                                           TypeMetadataDescriptor attributes) {
    this.content = content;
    this.attributes = attributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeMetadataDescriptor getPayloadMetadata() {
    return content;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeMetadataDescriptor getAttributesMetadata() {
    return attributes;
  }
}
