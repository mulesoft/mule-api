/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;
import org.mule.runtime.api.app.declaration.RouterElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;

/**
 * Allows configuring an {@link ScopeElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class RouteElementDeclarer
    extends ParameterizedElementDeclarer<RouteElementDeclarer, RouteElementDeclaration> {

  /**
   * Creates a new instance
   *
   */
  RouteElementDeclarer(RouteElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ComponentElementDeclaration component} to the {@link RouterElementDeclaration}
   *
   * @param component the {@link ComponentElementDeclaration component} to add
   * @return {@code this} declarer
   */
  public RouteElementDeclarer withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return this;
  }
}
