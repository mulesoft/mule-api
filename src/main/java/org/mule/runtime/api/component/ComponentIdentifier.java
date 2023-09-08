/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.mule.runtime.api.util.IdentifierParsingUtils.parseComponentIdentifier;
import static org.mule.runtime.internal.dsl.DslConstants.CORE_PREFIX;

import org.mule.api.annotation.NoImplement;

/**
 * Unique identifier for a configuration option. Every configuration option has a namespace and an identifier.
 * <p>
 * The namespace is a short name of the extension that defines the component. Even core configuration have a namespace even though
 * they have namespace in the declaration in the configuration files.
 *
 * @since 1.0
 */
@NoImplement
public interface ComponentIdentifier {

  /**
   * The namespace is a short name of the extension that defines the component.
   *
   * @return the unique identifier namespace
   */
  String getNamespace();

  /**
   *
   * @return the full namespace URI
   */
  String getNamespaceUri();

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
   * Creates a {@link ComponentIdentifier} from a string representation where the expected format is namespace:name. If the string
   * doesn't contain the namespace then it just needs to be the name of the component and the namespace will default to the core
   * namespace.
   *
   * @param componentIdentifier the component identifier represented as a string
   * @return the {@link ComponentIdentifier} created from its string representation.
   */
  static ComponentIdentifier buildFromStringRepresentation(String componentIdentifier) {
    return parseComponentIdentifier(componentIdentifier, CORE_PREFIX);
  }

  /**
   * {@link ComponentIdentifier} builder interface.
   *
   * @since 1.0
   */
  @NoImplement
  interface Builder {

    /**
     * @param name name of the component
     * @return {@code this} builder
     */
    Builder name(String name);

    /**
     * @param namespace namespace owning the component
     * @return {@code this} builder
     */
    Builder namespace(String namespace);

    /**
     * @param namespaceUri namespace owning the component
     * @return {@code this} builder
     */
    Builder namespaceUri(String namespaceUri);

    /**
     * @return a new instance of {@link ComponentIdentifier}
     */
    ComponentIdentifier build();

  }

}
