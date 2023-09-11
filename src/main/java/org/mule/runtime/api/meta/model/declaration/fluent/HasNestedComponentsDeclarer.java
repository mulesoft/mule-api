/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.nested.NestableElementModel;

/**
 * A contract interface for a declarer capable of adding a {@link NestableElementModel} as child
 *
 * @since 1.0
 */
@NoImplement
public interface HasNestedComponentsDeclarer {

  /**
   * Adds a component of the given {@code name}
   *
   * @param nestedComponentName a non blank name
   * @return a {@link NestedComponentDeclarer} which allows describing the created component
   */
  NestedComponentDeclarer withOptionalComponent(String nestedComponentName);

  /**
   * Adds a component of the given {@code name}
   *
   * @param nestedComponentName a non blank name
   * @return a {@link NestedComponentDeclarer} which allows describing the created component
   */
  NestedComponentDeclarer withComponent(String nestedComponentName);

  /**
   * Adds a component of the given {@code name}
   *
   * @return a {@link NestedComponentDeclarer} which allows describing the created component
   */
  NestedChainDeclarer withChain();

  /**
   * Adds a component of the given {@code name}
   *
   * @param chainName a non blank name
   * @return a {@link NestedComponentDeclarer} which allows describing the created component
   */
  NestedChainDeclarer withChain(String chainName);

}
