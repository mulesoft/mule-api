/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;
import static org.mule.runtime.internal.dsl.DslConstants.CORE_PREFIX;

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
    // This is reading the alias of the namespace in a given xml, not the actual namespace
    checkArgument(!isEmpty(componentIdentifier), "identifier cannot be an empty string or null");
    String[] values = componentIdentifier.split(":");
    String namespace;
    String identifier;
    if (values.length == 2) {
      namespace = values[0];
      identifier = values[1];
    } else {
      namespace = CORE_PREFIX;
      identifier = values[0];
    }
    return new DefaultComponentIdentifier.Builder().namespace(namespace).name(identifier).build();
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
    int result = namespaceLowerCase.hashCode();
    result = 31 * result + getName().hashCode();
    return result;
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
