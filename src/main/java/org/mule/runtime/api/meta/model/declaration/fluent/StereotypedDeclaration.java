/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.deprecated.DeprecatedModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Implementation of {@link NamedDeclaration} which adds a {@link List} of {@link ParameterDeclaration}
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class StereotypedDeclaration<T extends StereotypedDeclaration> extends ParameterizedDeclaration<T>
    implements WithStereotypesDeclaration, WithDeprecatedDeclaration {

  private StereotypeModel stereotype;
  private DeprecatedModel deprecation;

  /**
   * {@inheritDoc}
   */
  StereotypedDeclaration(String name) {
    super(name);
  }


  public StereotypeModel getStereotype() {
    return stereotype;
  }

  public void withStereotype(StereotypeModel stereotype) {
    this.stereotype = stereotype;
  }

  @Override
  public Optional<DeprecatedModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  @Override
  public void withDeprecation(DeprecatedModel deprecation) {
    this.deprecation = deprecation;
  }
}
