/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import org.mule.runtime.api.meta.model.operation.ScopeModel;

/**
 * A programmatic descriptor of a {@link ScopeModel} configuration.
 *
 * @since 1.0
 */
public final class ScopeElementDeclaration extends ComponentElementDeclaration {

  private RouteElementDeclaration route;

  public ScopeElementDeclaration() {}

  public ScopeElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  public RouteElementDeclaration getRoute() {
    return route;
  }

  public void setRoute(RouteElementDeclaration route) {
    this.route = route;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScopeElementDeclaration) || !super.equals(o)) {
      return false;
    }

    ScopeElementDeclaration that = (ScopeElementDeclaration) o;
    return route != null ? route.equals(that.route) : that.route == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (route != null ? route.hashCode() : 0);
    return result;
  }

}
