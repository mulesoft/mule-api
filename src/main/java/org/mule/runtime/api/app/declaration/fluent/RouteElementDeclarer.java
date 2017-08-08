/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import static java.lang.String.format;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;

/**
 * Allows configuring a {@link RouteElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class RouteElementDeclarer
    extends ParameterizedElementDeclarer<RouteElementDeclarer, RouteElementDeclaration>
    implements HasNestedComponentDeclarer<RouteElementDeclarer> {

  /**
   * Creates a new instance
   *
   */
  RouteElementDeclarer(RouteElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RouteElementDeclarer withComponent(ComponentElementDeclaration component) {
    checkArgument(!(component instanceof RouteElementDeclaration),
                  format("A route cannot declare inner routes, but route [%s] was declared as child of route [%s] for extension [%s].",
                         component.getName(), declaration.getName(), declaration.getDeclaringExtension()));
    declaration.addComponent(component);
    return this;
  }
}
