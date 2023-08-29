/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
