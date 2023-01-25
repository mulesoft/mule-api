/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Optional.ofNullable;

import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link NamedDeclaration} which adds a {@link List} of {@link ParameterDeclaration}
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class StereotypedDeclaration<T extends StereotypedDeclaration>
    extends ParameterizedWithMinMuleVersionDeclaration<T>
    implements WithStereotypesDeclaration, WithDeprecatedDeclaration {

  private StereotypeModel stereotype;
  private DeprecationModel deprecation;

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
  public Optional<DeprecationModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  @Override
  public void withDeprecation(DeprecationModel deprecation) {
    this.deprecation = deprecation;
  }
}
