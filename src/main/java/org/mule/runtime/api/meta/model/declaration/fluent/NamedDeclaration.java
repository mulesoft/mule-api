/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
