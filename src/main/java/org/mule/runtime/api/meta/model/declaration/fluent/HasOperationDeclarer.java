/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a declarer in which it's possible to add operations
 *
 * @since 1.0
 */
@NoImplement
public interface HasOperationDeclarer {

  /**
   * Adds an operation of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link OperationDeclarer} which allows describing the created operation
   */
  OperationDeclarer withOperation(String name);

  /**
   * Adds an {@link OperationDeclaration} by receiving a {@code declarer} which describes it
   *
   * @param declarer a {@link OperationDeclaration}
   */
  void withOperation(OperationDeclarer declarer);

}
