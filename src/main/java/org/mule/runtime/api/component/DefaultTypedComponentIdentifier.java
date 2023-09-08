/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import static org.mule.runtime.api.util.Preconditions.checkState;

import java.io.Serializable;

class DefaultTypedComponentIdentifier implements TypedComponentIdentifier, Serializable {

  private static final long serialVersionUID = -6585884125494525933L;

  private ComponentIdentifier identifier;
  private TypedComponentIdentifier.ComponentType type;

  private DefaultTypedComponentIdentifier() {}

  @Override
  public ComponentIdentifier getIdentifier() {
    return identifier;
  }

  @Override
  public ComponentType getType() {
    return type;
  }

  public static class Builder implements TypedComponentIdentifier.Builder {

    private final DefaultTypedComponentIdentifier typedComponentIdentifier = new DefaultTypedComponentIdentifier();

    @Override
    public TypedComponentIdentifier.Builder identifier(ComponentIdentifier identifier) {
      typedComponentIdentifier.identifier = identifier;
      return this;
    }

    @Override
    public Builder type(ComponentType type) {
      typedComponentIdentifier.type = type;
      return this;
    }

    @Override
    public TypedComponentIdentifier build() {
      checkState(typedComponentIdentifier.identifier != null, "identifier cannot be null");
      checkState(typedComponentIdentifier.type != null,
                 "type cannot be null");
      return typedComponentIdentifier;
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

    DefaultTypedComponentIdentifier that = (DefaultTypedComponentIdentifier) o;

    if (!getIdentifier().equals(that.getIdentifier())) {
      return false;
    }
    return getType() == that.getType();
  }

  @Override
  public int hashCode() {
    int result = getIdentifier().hashCode();
    result = 31 * result + Integer.hashCode(getType().ordinal());
    return result;
  }

  @Override
  public String toString() {
    return "DefaultTypedComponentIdentifier{" +
        "identifier=" + identifier +
        ", type=" + type +
        '}';
  }
}
