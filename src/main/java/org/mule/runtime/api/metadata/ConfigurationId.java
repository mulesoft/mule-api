/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata;

/**
 * Immutable implementation of {@link ComponentId} for identifying MessageProcessors
 *
 * @since 1.0
 */
public final class ConfigurationId {

  public ConfigurationId(String configName) {
    this.configName = configName;
  }

  public String getConfigName() {
    return configName;
  }

  private final String configName;

  @Override
  public String toString() {
    return "ConfigurationId{" +
        "configName='" + configName + '}';
  }
}
