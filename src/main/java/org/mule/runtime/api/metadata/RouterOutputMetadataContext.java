/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Contains DataSense propagation information for routers, by using a {@link MessageMetadataType} to describe the typed message
 * that enters each {@code Route} (input) and the message that exits them (output)
 * <p>
 * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
 *
 * @since 1.7.0
 */
@Experimental
@NoImplement
public interface RouterOutputMetadataContext {

  /**
   * Describes the payload and attribute types that exit each route through a {@link Map} which keys are the route name and the
   * value a {@link MessageMetadataType} supplier.
   * 
   * @return an unmodifiable {@link Map}, could be empty may not be {@code null}
   */
  Map<String, Supplier<MessageMetadataType>> getRouteOutputMessageTypes();

  /**
   * @return a {@link Supplier} describing the typed message that initially entered the router
   */
  Supplier<MessageMetadataType> getRouterInputMessageType();

  /**
   * @param parameterName the name of the parameter.
   * @return the parameter's resolved {@link MetadataType}.
   * @throws NoSuchElementException     if the parameter doesn't exist in the component.
   * @throws MetadataResolvingException if there is an issue when computing the type from the current bindings in context.
   *
   * @since 1.8
   */
  MetadataType getParameterResolvedType(String parameterName) throws NoSuchElementException, MetadataResolvingException;

}
