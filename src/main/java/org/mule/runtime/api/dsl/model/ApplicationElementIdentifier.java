/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.dsl.model;

import static org.mule.runtime.api.util.Preconditions.checkState;

/**
 * Unique identifier for a configuration option. Every configuration option has a namespaceURI and an identifier.
 *
 * The namespace defines the extension that declares the component.
 *
 * @since 1.0
 */
public class ApplicationElementIdentifier {

  private String namespaceUri;
  private String name;

  private ApplicationElementIdentifier() {}

  /**
   * @return the unique identifier namespace
   */
  public String getNamespace() {
    return namespaceUri;
  }

  /**
   * @return the unique identifier configuration name
   */
  public String getName() {
    return name;
  }

  public static class Builder {

    private ApplicationElementIdentifier identifier = new ApplicationElementIdentifier();

    private Builder() {}

    /**
     * @param namespaceUri namespace identifier of the mule language extensions module
     * @return the builder
     */
    public Builder withNamespace(String namespaceUri) {
      identifier.namespaceUri = namespaceUri;
      return this;
    }

    /**
     * @param name unique identifier within the namespace of the language configuration extension
     * @return the builder
     */
    public Builder withName(String name) {
      identifier.name = name;
      return this;
    }

    public ApplicationElementIdentifier build() {
      checkState(identifier.namespaceUri != null && !identifier.namespaceUri.isEmpty(), "Namespace URI must be not blank");
      checkState(identifier.name != null && !identifier.name.isEmpty(), "Name must be not blank");
      return identifier;
    }

  }

  /**
   * @return a new {@link Builder}
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ApplicationElementIdentifier that = (ApplicationElementIdentifier) o;

    if (!namespaceUri.equalsIgnoreCase(that.namespaceUri)) {
      return false;
    }
    return name.equals(that.name);

  }

  @Override
  public int hashCode() {
    int result = namespaceUri.toLowerCase().hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return getNamespace() + ":" + getName();
  }

}
