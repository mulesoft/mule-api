/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.NamedObject;

/**
 * Base class for a declaration of a named object.
 * <p>
 * By default, {@link #getDescription()} returns an empty {@link String}.
 *
 * @param <T> the concrete type for {@code this} declaration
 * @since 1.0
 */
public abstract class NamedDeclaration<T extends NamedDeclaration> extends BaseDeclaration<T> implements NamedObject {

  private final String name;

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  public NamedDeclaration(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

}
