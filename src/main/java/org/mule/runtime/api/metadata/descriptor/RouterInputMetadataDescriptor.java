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

import org.mule.metadata.message.api.MessageMetadataType;

import java.util.HashMap;
import java.util.Map;

public final class RouterInputMetadataDescriptor extends BaseInputMetadataDescriptor {

  private final Map<String, MessageMetadataType> routeInputMessageTypes;

  private RouterInputMetadataDescriptor(Map<String, ParameterMetadataDescriptor> parameters,
                                        Map<String, MessageMetadataType> routeInputMessageTypes) {
    super(parameters);
    this.routeInputMessageTypes = unmodifiableMap(routeInputMessageTypes);
  }

  public static RouterInputMetadataDescriptorBuilder builder() {
    return new RouterInputMetadataDescriptorBuilder();
  }

  public Map<String, MessageMetadataType> getRouteInputMessageTypes() {
    return routeInputMessageTypes;
  }

  public static final class RouterInputMetadataDescriptorBuilder
      extends BaseInputMetadataDescriptorBuilder<RouterInputMetadataDescriptor, RouterInputMetadataDescriptorBuilder> {

    private final Map<String, MessageMetadataType> routeInputMessageTypes = new HashMap<>();

    public RouterInputMetadataDescriptorBuilder withRouteInputMessageType(String routeName,
                                                                          MessageMetadataType routeInputMessageType) {
      checkArgument(isBlank(routeName), "routeName cannot be null or blank");
      checkArgument(routeInputMessageType != null, "routeInputMessageType cannot be null");

      this.routeInputMessageTypes.put(routeName, routeInputMessageType);
      return this;
    }

    public RouterInputMetadataDescriptorBuilder withRoutesInputMessageTypes(Map<String, MessageMetadataType> routesInputTypes) {
      this.routeInputMessageTypes.putAll(routesInputTypes);
      return this;
    }

    @Override
    public RouterInputMetadataDescriptor build() {
      checkArgument(!routeInputMessageTypes.isEmpty(), "routeInputMessageTypes cannot be empty");
      return new RouterInputMetadataDescriptor(parameters, routeInputMessageTypes);
    }
  }
}
