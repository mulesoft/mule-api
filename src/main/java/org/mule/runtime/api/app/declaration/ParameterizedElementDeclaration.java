/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a {@link ParameterizedModel} configuration.
 *
 * @since 1.0
 */
public abstract class ParameterizedElementDeclaration extends EnrichableElementDeclaration {

  private List<ParameterElementDeclaration> parameters = new LinkedList<>();

  public ParameterizedElementDeclaration() {}

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with
   * {@code this} 
   */
  public List<ParameterElementDeclaration> getParameters() {
    return parameters;
  }

  /**
   * Adds a {@link ParameterElementDeclaration parameter} to {@code this} parametrized element declaration
   *
   * @param parameter the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   */
  public void addParameter(ParameterElementDeclaration parameter) {
    this.parameters.add(parameter);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ParameterizedElementDeclaration) || !super.equals(o)) {
      return false;
    }

    ParameterizedElementDeclaration that = (ParameterizedElementDeclaration) o;
    return parameters.equals(that.parameters);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + parameters.hashCode();
    return result;
  }
}
