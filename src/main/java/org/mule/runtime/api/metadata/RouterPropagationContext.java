/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.Experimental;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.Map;

/**
 * Contains DataSense propagation information for routers, by using a {@link MessageMetadataType} to describe
 * the typed message that enters each {@code Route} (input) and the message that exits them (output)
 * <p>
 * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
 *
 * @since 1.7.0
 */
@Experimental
public interface RouterPropagationContext {

  /**
   * Returns an unmodifiable {@link Map} in which keys map to the route names and the values are a {@link ChainPropagationContext}
   * with the propagation context of the route inner chain.
   *
   * @return an unmodifiable {@link Map}
   */
  Map<String, ChainPropagationContext> getRoutesPropagationContext();

}
