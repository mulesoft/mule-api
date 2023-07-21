/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * A specialization of {@link ParameterDeclarer} for optional parameters. It allows adding properties that only apply to optional
 * parameters
 *
 * @since 1.0
 */
public class OptionalParameterDeclarer extends ParameterDeclarer<OptionalParameterDeclarer> {

  OptionalParameterDeclarer(ParameterDeclaration parameter) {
    super(parameter);
  }

  /**
   * Adds a default value for the parameter
   *
   * @param defaultValue a default value
   * @return this descriptor
   */
  public OptionalParameterDeclarer defaultingTo(Object defaultValue) {
    getDeclaration().setDefaultValue(defaultValue);
    return this;
  }
}
