/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Contract interface for a declarer in which it's possible
 * to add operations
 *
 * @since 1.0
 */
public interface HasOperationDeclarer {

  /**
   * Adds an operation of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link OperationDeclarer} which allows describing the created operation
   */
  OperationDeclarer withOperation(String name);

  /**
   * Adds an {@link OperationDeclaration} by receiving
   * a {@code declarer} which describes it
   *
   * @param declarer a {@link OperationDeclaration}
   */
  void withOperation(OperationDeclarer declarer);

  /**
   * Adds a scope of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link ScopeDeclarer} which allows describing the created scope
   */
  ScopeDeclarer withScope(String name);

  /**
   * Adds a {@link ScopeDeclarer} by receiving
   * a {@code declarer} which describes it
   *
   * @param declarer a {@link ScopeDeclarer}
   */
  void withScope(ScopeDeclarer declarer);

  /**
   * Adds a router of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link RouterDeclarer} which allows describing the created router
   */
  RouterDeclarer withRouter(String name);

  /**
   * Adds a {@link RouterDeclarer} by receiving
   * a {@code declarer} which describes it
   *
   * @param declarer a {@link RouterDeclarer}
   */
  void withRouter(RouterDeclarer declarer);
}
