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
