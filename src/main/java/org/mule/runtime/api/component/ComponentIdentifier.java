/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.mule.runtime.api.component.DefaultComponentIdentifier.parseComponentIdentifier;

/**
 * Unique identifier for a configuration option. Every configuration option has a prefix and an identifier.
 * <p>
 * The prefix is a short name of the extension that defines the component. Even core configuration have a prefix even though
 * they have prefix in the declaration in the configuration files.
 *
 * @since 1.0
 */
public interface ComponentIdentifier {

  /**
   * The prefix is a short name of the extension that defines the component.
   * 
   * @return the unique identifier prefix
   */
  String getPrefix();

  /**
   * @return the unique identifier configuration name
   */
  String getName();

  /**
   * @return builder to create an instance of {@link ComponentIdentifier}
   */
  static Builder builder() {
    return new DefaultComponentIdentifier.Builder();
  }

  /**
   * Creates a {@link ComponentIdentifier} from an string representation where the expected format is prefix:name. If the
   * string doesn't contain the prefix then it just needs to be the name of the component and the prefix will default to the
   * core prefix.
   * 
   * @param componentIdentifier the component identifier represented as a string
   * @return the {@link ComponentIdentifier} created from it's string representation.
   */
  static ComponentIdentifier buildFromStringRepresentation(String componentIdentifier) {
    return parseComponentIdentifier(componentIdentifier);
  }

  /**
   * {@link ComponentIdentifier} builder interface.
   * 
   * @since 1.0
   */
  interface Builder {

    /**
     * @param name name of the component
     * @return {@code this} builder
     */
    Builder withName(String name);

    /**
     * @param prefix prefix owning the component
     * @return {@code this} builder
     */
    Builder withPrefix(String prefix);

    /**
     * @return a new instance of {@link ComponentIdentifier}
     */
    ComponentIdentifier build();

  }

}
