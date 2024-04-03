/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.descriptor;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import static java.util.Collections.unmodifiableMap;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.mule.api.annotation.Experimental;
import org.mule.metadata.message.api.MessageMetadataType;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes a router's input by describing not only the input parameters but the message that will enter each of the routes.
 *
 * <b>NOTE:</b> Experimental feature. Backwards compatibility is not guaranteed.
 *
 * @since 1.7.0
 */
@Experimental
public final class RouterInputMetadataDescriptor extends BaseInputMetadataDescriptor {

  private final Map<String, MessageMetadataType> routeInputMessageTypes;

  private RouterInputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters,
                                        Map<String, MessageMetadataType> routeInputMessageTypes) {
    super(parameters);
    this.routeInputMessageTypes = unmodifiableMap(routeInputMessageTypes);
  }

  /**
   * @return a new {@link RouterInputMetadataDescriptorBuilder} instance
   */
  public static RouterInputMetadataDescriptorBuilder builder() {
    return new RouterInputMetadataDescriptorBuilder();
  }

  /**
   * Describes the payload and attribute types of the message that enters each route through a {@link Map} in which
   * the entries are the route names and the values the corresponding {@link MessageMetadataType}
   *
   * @return an unmodifiable map. Could be empty but cannot be {@code null}
   */
  public Map<String, MessageMetadataType> getRouteInputMessageTypes() {
    return routeInputMessageTypes;
  }

  /**
   * Implementation of the builder design pattern to create instances of {@link RouterInputMetadataDescriptor}
   *
   * @since 1.7
   */
  public static final class RouterInputMetadataDescriptorBuilder
      extends BaseInputMetadataDescriptorBuilder<RouterInputMetadataDescriptor, RouterInputMetadataDescriptorBuilder> {

    private final Map<String, MessageMetadataType> routeInputMessageTypes = new HashMap<>();

    /**
     * Types the input message of a specific route
     *
     * @param routeName             the route's name. Cannot be blank
     * @param routeInputMessageType a {@link MessageMetadataType} describing the route's input type. Cannot be {@code null}
     * @return {@code this} instance
     * @throws IllegalArgumentException in case of invalid arguments
     */
    public RouterInputMetadataDescriptorBuilder withRouteInputMessageType(String routeName,
                                                                          MessageMetadataType routeInputMessageType) {
      checkArgument(isBlank(routeName), "routeName cannot be null or blank");
      checkArgument(routeInputMessageType != null, "routeInputMessageType cannot be null");

      this.routeInputMessageTypes.put(routeName, routeInputMessageType);
      return this;
    }

    /**
     * Adds information for several routes. All entries must comply with the rules defined in {@link #withRouteInputMessageType(String, MessageMetadataType)}
     *
     * @param routesInputTypes a {@link Map} which keys are the route names and the values their corresponding {@link MessageMetadataType}.
     * @return {@code this} argument
     * @throws IllegalArgumentException if any of the entries are invalid
     */
    public RouterInputMetadataDescriptorBuilder withRoutesInputMessageTypes(Map<String, MessageMetadataType> routesInputTypes) {
      this.routeInputMessageTypes.putAll(routesInputTypes);
      return this;
    }

    /**
     * @return a new {@link RouterInputMetadataDescriptor}
     */
    @Override
    public RouterInputMetadataDescriptor build() {
      checkArgument(!routeInputMessageTypes.isEmpty(), "routeInputMessageTypes cannot be empty");
      return new RouterInputMetadataDescriptor(parameters, routeInputMessageTypes);
    }
  }
}
