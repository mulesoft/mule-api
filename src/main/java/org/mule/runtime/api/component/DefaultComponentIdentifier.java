/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.mule.runtime.api.dsl.DslConstants.CORE_NAMESPACE;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static org.mule.runtime.api.util.Preconditions.checkState;

/**
 * Unique identifier for a configuration option. Every configuration option has a namespace and an identifier.
 *
 * The namespace define the extension that defines the component. Even core configuration has a namespace even though there's no
 * namespace used in the configuration files.
 *
 * @since 4.0
 */
class DefaultComponentIdentifier implements ComponentIdentifier {

  private String namespace;
  private String name;

  private DefaultComponentIdentifier() {}

  /**
   * {@inheritDoc}
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * {@inheritDoc}
   */
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
      namespace = CORE_NAMESPACE;
      identifier = values[0];
    }
    return new DefaultComponentIdentifier.Builder().withNamespace(namespace).withName(identifier).build();
  }

  public static class Builder implements ComponentIdentifier.Builder {

    private DefaultComponentIdentifier componentIdentifier = new DefaultComponentIdentifier();

    /**
     * @param namespace namespace identifier of the mule language extensions module
     * @return the builder
     */
    public Builder withNamespace(String namespace) {
      componentIdentifier.namespace = namespace;
      return this;
    }

    /**
     * @param identifier identifier unique identifier within the namespace of the language configuration extension
     * @return the builder
     */
    public Builder withName(String identifier) {
      componentIdentifier.name = identifier;
      return this;
    }

    public ComponentIdentifier build() {
      checkState(componentIdentifier.namespace != null && !componentIdentifier.namespace.trim().isEmpty(),
                 "Namespace URI must be not blank");
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

    if (!getNamespace().equalsIgnoreCase(that.getNamespace())) {
      return false;
    }
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    int result = getNamespace().toLowerCase().hashCode();
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
    return getNamespace().equals("mule") ? getName() : getNamespace() + ":" + getName();
  }


}
