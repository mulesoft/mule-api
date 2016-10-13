/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Base class for a component which can be used to create a {@link BaseDeclaration}
 *
 * @param <T> the generic type of {@link BaseDeclaration} to be created
 * @since 1.0
 */
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
