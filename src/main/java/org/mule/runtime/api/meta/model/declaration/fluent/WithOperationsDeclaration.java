/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link OperationDeclaration} objects
 *
 * @param <T> the generic type of the {@link BaseDeclaration} which is implementing the interface
 * @since 1.0
 */
@NoImplement
public interface WithOperationsDeclaration<T extends BaseDeclaration> {

  /**
   * Adds a {@link OperationDeclaration}
   *
   * @param operation the provider's declaration
   * @return {@code this} declaration
   */
  T addOperation(OperationDeclaration operation);

  /**
   * @return a {@link List} with the {@link OperationDeclaration} which have been added to {@code this} declaration
   */
  List<OperationDeclaration> getOperations();

}
