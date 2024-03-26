/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.metadata.message.api.MessageMetadataType;

import java.util.function.Supplier;

/**
 * Models the propagation context of a Scope.
 *
 * @since 1.7
 */
public interface ChainPropagationContext {

  /**
   * @return a {@link Supplier} describing the typed message that will enter the scope inner chain
   */
  Supplier<MessageMetadataType> getChainInputResolver();

  /**
   * @return a {@link Supplier} describing the typed message that exits the scope inner chain
   */
  Supplier<MessageMetadataType> getChainOutputResolver();

}
