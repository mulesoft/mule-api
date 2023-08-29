/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
