/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.operation.RouterModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A declaration object for a {@link RouterModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link RouterModel}
 *
 * @since 1.0
 */
public class RouterDeclaration extends OperationDeclaration {

  private List<RouteDeclaration> routes = new LinkedList<>();

  public RouterDeclaration(String name) {
    super(name);
  }

  public RouterDeclaration addRoute(RouteDeclaration route) {
    routes.add(route);
    return this;
  }

  public List<RouteDeclaration> getRoutes() {
    return routes;
  }
}
