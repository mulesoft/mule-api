/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Base class for a declarer which allows adding {@link ParameterDeclaration}s
 *
 * @since 1.0
 */
public abstract class ParameterizedDeclarer<D extends ParameterizedDeclaration> extends Declarer<D> {

  public ParameterizedDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds a required parameter
   *
   * @param name the name of the parameter
   * @return a new {@link ParameterDeclarer}
   */
  public ParameterDeclarer withRequiredParameter(String name) {
    return new ParameterDeclarer(newParameter(name, true));
  }

  /**
   * Adds an optional parameter
   *
   * @param name the name of the parameter
   * @return a new {@link OptionalParameterDeclarer}
   */
  public OptionalParameterDeclarer withOptionalParameter(String name) {
    return new OptionalParameterDeclarer(newParameter(name, false));
  }

  private ParameterDeclaration newParameter(String name, boolean required) {
    ParameterDeclaration parameter = new ParameterDeclaration(name);
    parameter.setRequired(required);
    declaration.addParameter(parameter);

    return parameter;
  }
}
