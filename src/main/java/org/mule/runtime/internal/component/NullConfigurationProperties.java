/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.component;

import static java.util.Optional.empty;

import org.mule.runtime.api.component.ConfigurationProperties;

import java.util.Optional;

/**
 * Null object implementation for {@link ConfigurationProperties}
 *
 * @since 4.5
 */
public class NullConfigurationProperties implements ConfigurationProperties {

  @Override
  public <T> Optional<T> resolveProperty(String propertyKey) {
    return empty();
  }

  @Override
  public Optional<Boolean> resolveBooleanProperty(String property) {
    return empty();
  }

  @Override
  public Optional<String> resolveStringProperty(String property) {
    return empty();
  }
}
