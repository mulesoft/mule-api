/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import org.mule.runtime.api.meta.model.operation.RouterModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a {@link RouterModel} configuration.
 *
 * @since 1.0
 */
public final class RouterElementDeclaration extends ComponentElementDeclaration {

  private List<RouteElementDeclaration> routes = new LinkedList<>();

  public RouterElementDeclaration() {}

  public RouterElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  public List<RouteElementDeclaration> getRoutes() {
    return routes;
  }

  public void addRoute(RouteElementDeclaration route) {
    this.routes.add(route);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RouterElementDeclaration) || !super.equals(o)) {
      return false;
    }

    RouterElementDeclaration that = (RouterElementDeclaration) o;
    return routes != null ? routes.equals(that.routes) : that.routes == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (routes != null ? routes.hashCode() : 0);
    return result;
  }
}
