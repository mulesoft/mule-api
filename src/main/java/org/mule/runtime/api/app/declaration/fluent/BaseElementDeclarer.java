/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;


import org.mule.runtime.api.app.declaration.ElementDeclaration;

/**
 * Base declarer for a given {@link ElementDeclaration}
 *
 * @since 1.0
 */
public abstract class BaseElementDeclarer<T> {

  protected T declaration;

  BaseElementDeclarer(T declaration) {
    this.declaration = declaration;
  }

  /**
   * @return the {@code declaration} being built
   */
  public T getDeclaration() {
    return declaration;
  }

}
