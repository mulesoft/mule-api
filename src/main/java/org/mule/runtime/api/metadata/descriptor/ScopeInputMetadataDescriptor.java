/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;

import org.mule.api.annotation.Experimental;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.Map;

/**
 * Describes a scope input by describing not only the input parameters but the message that will enter the inner chain.
 *
 * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
 *
 * @since 1.7.0
 */
@Experimental
public final class ScopeInputMetadataDescriptor extends BaseInputMetadataDescriptor {

  private final MessageMetadataType chainInputMessageType;

  private ScopeInputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters,
                                       MessageMetadataType chainInputMessageType) {
    super(parameters);
    this.chainInputMessageType = chainInputMessageType;
  }

  /**
   * @return a new builder instance
   */
  public static ScopeInputMetadataDescriptorBuilder builder() {
    return new ScopeInputMetadataDescriptorBuilder();
  }

  /**
   * @return a {@link MessageMetadataType} describing the message that will enter the scope's inner chain
   */
  public MessageMetadataType getChainInputMessageType() {
    return chainInputMessageType;
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link ScopeInputMetadataDescriptor}
   *
   * @since 1.7
   */
  public static final class ScopeInputMetadataDescriptorBuilder
      extends BaseInputMetadataDescriptorBuilder<ScopeInputMetadataDescriptor, ScopeInputMetadataDescriptorBuilder> {

    private MessageMetadataType chainInputMessageType;

    /**
     * Sets the {@link MessageMetadataType} that describes the inner chain's input
     *
     * @param chainInputMessageType a {@link MessageMetadataType}
     * @return {@code this} builder
     * @throws IllegalArgumentException if {@code chainInputMessageType} is {@code null}
     */
    public ScopeInputMetadataDescriptorBuilder withChainInputMessageType(MessageMetadataType chainInputMessageType) {
      checkArgument(chainInputMessageType != null, "chainInputMessageType cannot be null");
      this.chainInputMessageType = chainInputMessageType;
      return this;
    }

    /**
     * @return a new {@link ScopeInputMetadataDescriptor}
     * @throws IllegalStateException if invoked before {@link #withChainInputMessageType(MessageMetadataType)}
     */
    @Override
    public ScopeInputMetadataDescriptor build() {
      checkState(chainInputMessageType != null, "chainInputMessageType cannot be null");
      return new ScopeInputMetadataDescriptor(parameters, chainInputMessageType);
    }
  }
}
