/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.api.app.declaration.ParameterizedElementDeclaration;

/**
 * Allows configuring a {@link ParameterizedElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ParameterizedElementDeclarer<D extends ParameterizedElementDeclarer, T extends ParameterizedElementDeclaration>
    extends BaseElementDeclarer<T> {

  ParameterizedElementDeclarer(T declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param name  the {@code name} of the {@link ParameterElementDeclaration}
   * @param value the {@code value} of the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   * @return {@code this} declarer
   */
  public D withParameter(String name, String value) {
    return withParameter(name, ParameterSimpleValue.of(value));
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param name  the {@code name} of the {@link ParameterElementDeclaration}
   * @param value the {@link ParameterValue} of the {@link ParameterElementDeclaration}
   *              to associate to {@code this} element declaration
   * @return {@code this} declarer
   */
  public D withParameter(String name, ParameterValue value) {
    ParameterElementDeclaration parameter = new ParameterElementDeclaration();
    parameter.setName(name);
    parameter.setValue(value);
    declaration.addParameter(parameter);
    return (D) this;
  }

}
