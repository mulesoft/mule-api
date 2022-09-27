/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import org.mule.runtime.internal.component.NullConfigurationProperties;

import java.util.Optional;

/**
 * Represents the configuration properties defined in the artifact.
 * <p>
 * An artifact has a set of configuration properties defined by using the <configuration-properties> element, system properties or
 * deployment time properties. Each artifact (application, domain, etc) provides an implementation of
 * {@link ConfigurationProperties} that can be injected to access it programmatically.
 *
 * @since 1.0
 */
public interface ConfigurationProperties {

  /**
   * Creates a no operation implementation of {@link ConfigurationProperties}.
   *
   * @return a no operation implementation of {@link ConfigurationProperties}.
   * @since 1.5
   */
  static ConfigurationProperties nullConfigurationProperties() {
    return new NullConfigurationProperties();
  }

  /**
   * Lookups for a property and returns it's value as boolean.
   *
   * @param propertyKey the property identifier
   * @return the resolved value for the property. It may be null if it does not exists.
   */
  <T> Optional<T> resolveProperty(String propertyKey);

  /**
   * Lookups for a property and returns it's value as boolean. If it cannot be converted to boolean then it will fail.
   *
   * @param property the property identifier
   * @return the resolver value for the property.
   */
  Optional<Boolean> resolveBooleanProperty(String property);

  /**
   * Lookups for a property and returns it's value as boolean. If it's not a string then it will fail.
   *
   * @param property the property identifier
   * @return the resolver value for the property.
   */
  Optional<String> resolveStringProperty(String property);

}
