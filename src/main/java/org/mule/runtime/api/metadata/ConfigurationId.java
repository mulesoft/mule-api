/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

import static java.lang.String.format;

import java.util.Optional;

/**
 * Immutable implementation of {@link ComponentId} for identifying Configurations
 *
 * @since 1.0
 */
public class ConfigurationId implements ComponentId {

  private final String componentPath;

  // TODO: MULE-9496 define path for global element
  public ConfigurationId(String componentPath) {
    this.componentPath = componentPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<String> getFlowName() {
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getComponentPath() {
    return componentPath;
  }

  @Override
  public String toString() {
    return format("ConfigurationId{configName='%s'}", componentPath);
  }
}
