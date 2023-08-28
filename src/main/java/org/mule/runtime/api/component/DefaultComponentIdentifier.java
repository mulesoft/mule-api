/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.mule.runtime.api.util.Preconditions.checkState;
import static org.mule.runtime.internal.dsl.DslConstants.CORE_PREFIX;

import org.mule.runtime.api.util.IdentifierParsingUtils;

import java.io.Serializable;

/**
 * Unique identifier for a configuration option. Every configuration option has a namespace and an identifier.
 *
 * The namespace define the extension that defines the component. Even core configuration has a namespace even though there's no
 * namespace used in the configuration files.
 *
 * @since 4.0
 */
class DefaultComponentIdentifier implements ComponentIdentifier, Serializable {

  private static final long serialVersionUID = -7904681927277956932L;

  private String namespace;
  private String namespaceLowerCase;
  private String namespaceUri;
  private String name;

  private int hash;

  private DefaultComponentIdentifier() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNamespace() {
    return namespace;
  }

  @Override
  public String getNamespaceUri() {
    return namespaceUri;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  static ComponentIdentifier parseComponentIdentifier(String componentIdentifier) {
    return IdentifierParsingUtils.parseComponentIdentifier(componentIdentifier, CORE_PREFIX);
  }

  public static class Builder implements ComponentIdentifier.Builder {

    private final DefaultComponentIdentifier componentIdentifier = new DefaultComponentIdentifier();

    /**
     * @param namespace namespace identifier of the Mule language extensions module
     * @return the builder
     */
    @Override
    public Builder namespace(String namespace) {
      componentIdentifier.namespace = namespace;
      componentIdentifier.namespaceLowerCase = namespace.toLowerCase();
      return this;
    }

    @Override
    public Builder namespaceUri(String namespaceUri) {
      componentIdentifier.namespaceUri = namespaceUri;
      return this;
    }

    /**
     * @param identifier identifier unique identifier within the namespace of the language configuration extension
     * @return the builder
     */
    @Override
    public Builder name(String identifier) {
      componentIdentifier.name = identifier;
      return this;
    }

    @Override
    public ComponentIdentifier build() {
      checkState(componentIdentifier.namespace != null && !componentIdentifier.namespace.trim().isEmpty(),
                 "Prefix URI must be not blank");
      checkState(componentIdentifier.name != null && !componentIdentifier.name.trim().isEmpty(),
                 "Name must be not blank");

      componentIdentifier.hash = componentIdentifier.namespaceLowerCase.hashCode();
      componentIdentifier.hash = 31 * componentIdentifier.hash + componentIdentifier.name.hashCode();

      return componentIdentifier;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DefaultComponentIdentifier that = (DefaultComponentIdentifier) o;

    if (!namespaceLowerCase.equals(that.namespaceLowerCase)) {
      return false;
    }
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return hash;
  }

  /**
   * @return a new {@link Builder}
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    return getNamespace().equals(CORE_PREFIX) ? getName() : getNamespace() + ":" + getName();
  }


}
