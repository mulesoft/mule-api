/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoExtend;

/**
 * Base class for a component which can be used to create a {@link BaseDeclaration}
 *
 * @param <T> the generic type of {@link BaseDeclaration} to be created
 * @since 1.0
 */
@NoExtend
public abstract class Declarer<T extends BaseDeclaration> {

  protected final T declaration;

  /**
   * Creates a new instance
   *
   * @param declaration a declaration which state is to be populated through {@code this} instance
   */
  public Declarer(T declaration) {
    this.declaration = declaration;
  }

  /**
   * @return The created declaration
   */
  public T getDeclaration() {
    return declaration;
  }
}
