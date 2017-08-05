/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import static java.util.Collections.emptyMap;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterGroupElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.api.app.declaration.ParameterizedElementDeclaration;

import java.io.Serializable;
import java.util.Map;

/**
 * Allows configuring a {@link ParameterizedElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ParameterGroupElementDeclarer
    extends EnrichableElementDeclarer<ParameterGroupElementDeclarer, ParameterGroupElementDeclaration>
    implements ParameterizedBuilder<String, ParameterValue, ParameterGroupElementDeclarer> {

  public ParameterGroupElementDeclarer(ParameterGroupElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * Set's the name of the element
   *
   * @param name the name of the element
   * @return {@code this} declarer
   */
  public ParameterGroupElementDeclarer withName(String name) {
    declaration.setName(name);
    return this;
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param name  the {@code name} of the {@link ParameterElementDeclaration}
   * @param value the {@code value} of the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   * @return {@code this} declarer
   */
  public ParameterGroupElementDeclarer withParameter(String name, String value) {
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
  public ParameterGroupElementDeclarer withParameter(String name, ParameterValue value) {
    withParameter(name, value, emptyMap());
    return this;
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param name  the {@code name} of the {@link ParameterElementDeclaration}
   * @param value the {@link ParameterValue} of the {@link ParameterElementDeclaration}
   *              to associate to {@code this} element declaration
   * @return {@code this} declarer
   */
  public ParameterGroupElementDeclarer withParameter(String name, ParameterValue value, Map<String, Serializable> properties) {
    ParameterElementDeclaration parameter = new ParameterElementDeclaration();
    parameter.setName(name);
    parameter.setValue(value);
    properties.forEach(parameter::addMetadataProperty);
    declaration.addParameter(parameter);
    return this;
  }

}
