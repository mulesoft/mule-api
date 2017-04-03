/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.EnrichableElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;

import java.io.Serializable;

/**
 * Allows configuring an {@link EnrichableElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class EnrichableElementDeclarer<D extends EnrichableElementDeclarer, T extends EnrichableElementDeclaration>
    extends BaseElementDeclarer<T> {

  EnrichableElementDeclarer(T declaration) {
    super(declaration);
  }

  /**
   * Adds a metadata property to {@code this} element declaration
   *
   * @param name  the {@code name} of the {@code property}
   * @param value the {@code value} of the {@code property}
   * @return {@code this} declarer
   */
  public D withProperty(String name, Serializable value) {
    this.declaration.addMetadataProperty(name, value);
    return (D) this;
  }

  /**
   * Adds a {@link ParameterElementDeclaration custom parameter} to {@code this} enrichable element declaration
   * This {@code customParameter} represents an additional parameter to the ones exposed by the actual model
   * associated to this {@link ElementDeclaration element}.
   * No validation of any kind will be performed over this {@code customParameter} and its value.
   *
   * @param name  the {@code name} of the {@link ParameterElementDeclaration}
   * @param value the {@code value} of the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   * @return {@code this} declarer
   */
  public D withCustomParameter(String name, String value) {
    ParameterElementDeclaration parameter = new ParameterElementDeclaration();
    parameter.setName(name);
    parameter.setValue(ParameterSimpleValue.of(value));
    declaration.addCustomConfigurationParameter(parameter);
    return (D) this;
  }

}
