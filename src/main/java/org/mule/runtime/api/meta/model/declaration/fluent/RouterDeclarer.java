/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Allows configuring a {@link RouterDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class RouterDeclarer extends AbstractOperationDeclarer<RouterDeclarer, RouterDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link RouterDeclaration} to be configured
   */
  public RouterDeclarer(RouterDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a route of the given {@code name}
   *
   * @param routeName a non blank name
   * @return a {@link RouteDeclarer} which allows describing the created route
   */
  public RouteDeclarer withRoute(String routeName) {
    RouteDeclaration route = new RouteDeclaration(routeName);
    declaration.addRoute(route);
    return new RouteDeclarer(route);
  }
}
