/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


/**
 * Contract interface for a declarer in which it's possible
 * to add message sources
 *
 * @since 1.0
 */
public interface HasSourceDeclarer {

  /**
   * Adds a message source of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link SourceDeclarer} which allows describing the created provider
   */
  SourceDeclarer withMessageSource(String name);

  /**
   * Adds a {@link SourceDeclaration} by receiving
   * a {@code declarer} which describes it
   *
   * @param declarer a {@link SourceDeclarer}
   */
  void withMessageSource(SourceDeclarer declarer);
}
