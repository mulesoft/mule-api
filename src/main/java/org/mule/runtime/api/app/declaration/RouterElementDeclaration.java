/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import org.mule.runtime.api.component.location.Location;
import org.mule.runtime.api.meta.model.operation.RouterModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

  /**
   * @return all the {@link RouteElementDeclaration routes} {@code this} {@link RouterElementDeclaration}
   * can handle.
   */
  public List<RouteElementDeclaration> getRoutes() {
    return routes;
  }

  /**
   * Adds a {@link RouteElementDeclaration route} as one of the possible routes
   * {@code this} {@link RouterElementDeclaration router} can handle.
   * @param route
   */
  public void addRoute(RouteElementDeclaration route) {
    this.routes.add(route);
  }

  /**
   * Looks for an {@link ElementDeclaration} contained by {@code this} declaration
   * based on the {@code parts} of a {@link Location}.
   *
   * @param parts the {@code parts} of a {@link Location} relative to {@code this} element
   * @return the {@link ElementDeclaration} located in the path created by the {@code parts}
   * or {@link Optional#empty()} if no {@link ElementDeclaration} was found in that location.
   */
  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    if (routes.isEmpty()) {
      return empty();
    }

    String identifier = parts.get(0);
    if (isNumeric(identifier) && parseInt(identifier) < routes.size()) {
      return routes.get(parseInt(identifier)).findElement(parts.subList(1, parts.size()));
    }

    return super.findElement(parts);
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
