/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.Stereotype;

import java.util.stream.Stream;

/**
 * Allows configuring a {@link RouteDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class RouteDeclarer extends ParameterizedDeclarer<RouteDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link RouteDeclaration} to be configured
   */
  public RouteDeclarer(RouteDeclaration declaration) {
    super(declaration);
  }

  /**
   * Sets the minimum amount of times that this route can be present on the owning
   * component
   *
   * @param minOccurs a value equal or greater than zero
   * @return {@code this} declarer
   */
  public RouteDeclarer withMinOccurs(int minOccurs) {
    declaration.setMinOccurs(minOccurs);
    return this;
  }

  /**
   * Sets the maximum amount of times that this route can be present on the owning
   * component
   *
   * @param maxOccurs a value greater or equal than zero
   * @return {@code this} declarer
   */
  public RouteDeclarer withMaxOccurs(Integer maxOccurs) {
    declaration.setMaxOccurs(maxOccurs);
    return this;
  }

  /**
   * Adds the given {@code stereotypes} to the ones which are allowed on
   * the route.
   *
   * @param stereotypes the stereotypes to add
   * @return {@code this} declarer
   */
  public RouteDeclarer withAllowedStereotypes(Stereotype... stereotypes) {
    Stream.of(stereotypes).forEach(declaration::addAllowedStereotype);
    return this;
  }
}
