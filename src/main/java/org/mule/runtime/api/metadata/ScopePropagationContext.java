/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.function.Supplier;

/**
 * Models the propagation context of a Scope.
 *
 * @since 1.7
 */
public interface ScopePropagationContext {

  /**
   * @return an {@link Supplier} for the {@link MetadataType} of the inner chain of the scope.
   */
  Supplier<MetadataType> getInnerChainResolver();

  /**
   * @return a {@link Supplier} for the {@link MessageMetadataType} of the Message output.
   */
  Supplier<MessageMetadataType> getMessageTypeResolver();
}
