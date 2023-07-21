/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.component;

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
