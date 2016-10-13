/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link NamedDeclaration} which adds
 * a {@link List} of {@link ParameterDeclaration}
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class ParameterizedDeclaration<T extends ParameterizedDeclaration> extends NamedDeclaration<T> {

  private List<ParameterDeclaration> parameters = new LinkedList<>();

  /**
   * {@inheritDoc}
   */
  ParameterizedDeclaration(String name) {
    super(name);
  }

  /**
   * @return an unmodifiable {@link List} with the {@link ParameterDeclaration declarations}
   * registered through {@link #addParameter(ParameterDeclaration)}
   */
  public List<ParameterDeclaration> getParameters() {
    return unmodifiableList(parameters);
  }

  /**
   * Adds a {@link ParameterDeclaration}
   *
   * @param parameter a not {@code null} {@link ParameterDeclaration}
   * @return this declaration
   * @throws {@link IllegalArgumentException} if {@code parameter} is {@code null}
   */
  public T addParameter(ParameterDeclaration parameter) {
    if (parameter == null) {
      throw new IllegalArgumentException("Can't add a null parameter");
    }

    parameters.add(parameter);
    return (T) this;
  }
}
