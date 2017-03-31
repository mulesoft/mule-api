/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;

/**
 * Allows configuring an {@link ScopeElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ScopeElementDeclarer<E extends ScopeElementDeclarer, D extends ScopeElementDeclaration>
    extends ComponentElementDeclarer<E, D> {

  /**
   * Creates a new instance of {@link E}
   */
  ScopeElementDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ComponentElementDeclaration component} to the declaration being built
   *
   * @param component the {@link ComponentElementDeclaration component} to add
   * @return {@code this} declarer
   */
  public E withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return (E) this;
  }

  @Override
  public D getDeclaration() {
    return super.getDeclaration();
  }
}
