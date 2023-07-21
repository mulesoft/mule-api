/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a declarer in which it's possible to add message sources
 *
 * @since 1.0
 */
@NoImplement
public interface HasSourceDeclarer {

  /**
   * Adds a message source of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link SourceDeclarer} which allows describing the created provider
   */
  SourceDeclarer withMessageSource(String name);

  /**
   * Adds a {@link SourceDeclaration} by receiving a {@code declarer} which describes it
   *
   * @param declarer a {@link SourceDeclarer}
   */
  void withMessageSource(SourceDeclarer declarer);
}
