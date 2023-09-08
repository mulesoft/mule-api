/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link FunctionDeclaration} objects
 *
 * @param <T> the generic type of the {@link BaseDeclaration} which is implementing the interface
 * @since 1.0
 */
@NoImplement
public interface WithFunctionsDeclaration<T extends BaseDeclaration> {

  /**
   * Adds a {@link FunctionDeclaration}
   *
   * @param function the provider's declaration
   * @return {@code this} declaration
   */
  T addFunction(FunctionDeclaration function);

  /**
   * @return a {@link List} with the {@link FunctionDeclaration} which have been added to {@code this} declaration
   */
  List<FunctionDeclaration> getFunctions();

}
