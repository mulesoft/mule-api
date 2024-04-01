/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.metadata.message.api.MessageMetadataType;

import java.util.Map;

public final class ScopeInputMetadataDescriptor extends BaseInputMetadataDescriptor {

  private final MessageMetadataType chainInputMessageType;

  private ScopeInputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters,
                                       MessageMetadataType chainInputMessageType) {
    super(parameters);
    this.chainInputMessageType = chainInputMessageType;
  }

  public static ScopeInputMetadataDescriptorBuilder builder() {
    return new ScopeInputMetadataDescriptorBuilder();
  }

  public MessageMetadataType getChainInputMessageType() {
    return chainInputMessageType;
  }

  public static final class ScopeInputMetadataDescriptorBuilder
      extends BaseInputMetadataDescriptorBuilder<ScopeInputMetadataDescriptor, ScopeInputMetadataDescriptorBuilder> {

    private MessageMetadataType chainInputMessageType;

    public ScopeInputMetadataDescriptorBuilder withChainInputMessageType(MessageMetadataType chainInputMessageType) {
      this.chainInputMessageType = chainInputMessageType;
      return this;
    }

    @Override
    public ScopeInputMetadataDescriptor build() {
      checkArgument(chainInputMessageType != null, "chainInputMessageType cannot be null");
      return new ScopeInputMetadataDescriptor(parameters, chainInputMessageType);
    }
  }
}
