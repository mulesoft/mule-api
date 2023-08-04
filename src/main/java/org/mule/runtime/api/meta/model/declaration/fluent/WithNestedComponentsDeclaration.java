/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link NestableElementDeclaration} objects
 *
 * @param <T> the generic type of the {@link BaseDeclaration} which is implementing the interface
 * @since 1.0
 */
@NoImplement
public interface WithNestedComponentsDeclaration<T> {

  /**
   * Adds a {@link NestableElementDeclaration}
   *
   * @param nestedComponent the component declaration
   * @return {@code this} declaration
   */
  T addNestedComponent(NestableElementDeclaration nestedComponent);

  /**
   * @return a {@link List} with the {@link NestableElementDeclaration}s which have been added to {@code this} declaration
   */
  List<NestableElementDeclaration> getNestedComponents();

}
