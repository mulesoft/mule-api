/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.nested.NestedRouteModel} configuration.
 *
 * @since 1.0
 */
public final class RouteElementDeclaration extends ComponentElementDeclaration<RouteElementDeclaration> {

  public RouteElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

}
