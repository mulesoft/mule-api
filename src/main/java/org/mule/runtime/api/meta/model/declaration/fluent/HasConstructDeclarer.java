/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * Contract interface for a declarer in which it's possible to add constructs
 *
 * @since 1.0
 */
@NoImplement
public interface HasConstructDeclarer<T> {

  /**
   * Adds a construct of the given {@code name}
   *
   * @param name a non blank name
   * @return a {@link ConstructDeclarer} which allows describing the created construct
   */
  ConstructDeclarer withConstruct(String name);

  /**
   * Adds an {@link ConstructDeclaration} by receiving a {@code declarer} which describes it
   *
   * @param declarer a {@link ConstructDeclaration}
   */
  T withConstruct(ConstructDeclarer declarer);

}
