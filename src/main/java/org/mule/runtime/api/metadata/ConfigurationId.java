/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Immutable implementation of {@link ComponentId} for identifying Message Sources
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
  public String getFlowName() {
    throw new UnsupportedOperationException("Configuration element is global and doesn't belong to a particular flow");
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
    return "ConfigurationId{" +
        "configName='" + componentPath + '}';
  }
}
