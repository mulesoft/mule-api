/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.operation.ScopeModel;

/**
 * A declaration object for a {@link ScopeModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link ScopeModel}
 *
 * @since 1.0
 */
public class ScopeDeclaration extends OperationDeclaration {

  private RouteDeclaration route;

  /**
   * {@inheritDoc}
   */
  public ScopeDeclaration(String name) {
    super(name);
  }

  public RouteDeclaration getRoute() {
    return route;
  }

  public void setRoute(RouteDeclaration route) {
    this.route = route;
  }
}
