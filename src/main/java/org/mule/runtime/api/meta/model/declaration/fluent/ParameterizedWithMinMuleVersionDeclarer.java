/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.MuleVersion;

/**
 * Base class for a component which can be used to create a {@link BaseDeclaration} and has a minimum version required for its
 * usage.
 *
 * @param <T> the generic type of {@link BaseDeclaration} to be created
 * @since 1.5
 */
public class ParameterizedWithMinMuleVersionDeclarer<T extends ParameterizedWithMinMuleVersionDeclarer, D extends ParameterizedWithMinMuleVersionDeclaration>
    extends ParameterizedDeclarer<T, D> implements HasMinMuleVersionDeclarer<T> {

  /**
   * Creates a new instance
   *
   * @param declaration a declaration which state is to be populated through {@code this} instance
   */
  public ParameterizedWithMinMuleVersionDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * {@inheritDoc}
   */
  public T withMinMuleVersion(MuleVersion minMuleVersion) {
    this.declaration.withMinMuleVersion(minMuleVersion);
    return (T) this;
  }
}
