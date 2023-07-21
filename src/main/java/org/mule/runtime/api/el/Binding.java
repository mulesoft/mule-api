/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.TypedValue;

/**
 * Represents the relationship between an EL variable or function and its value.
 *
 * @since 1.0
 */
public final class Binding {

  private String identifier;
  private TypedValue value;

  public Binding(String identifier, TypedValue value) {
    this.identifier = identifier;
    this.value = value;
  }

  /**
   * @return the name of the binding
   */
  public String identifier() {
    return identifier;
  }

  /**
   * @return the bindings value
   */
  public TypedValue value() {
    return value;
  }

}
