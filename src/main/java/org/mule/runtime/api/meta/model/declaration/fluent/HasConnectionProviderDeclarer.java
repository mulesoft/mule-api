/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

@NoImplement
public interface HasConnectionProviderDeclarer {

  /**
   * Adds a connection provider of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link ConnectionProviderDeclarer} which allows describing the created provider
   */
  ConnectionProviderDeclarer withConnectionProvider(String name);

  /**
   * Adds a {@link ConnectionProviderDeclaration} by receiving a {@code declarer} which describes it
   *
   * @param declarer a {@link ConnectionProviderDeclarer}
   */
  void withConnectionProvider(ConnectionProviderDeclarer declarer);
}
