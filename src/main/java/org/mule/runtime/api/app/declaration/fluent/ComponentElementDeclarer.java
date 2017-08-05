/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;

import java.util.function.Consumer;

/**
 * Allows configuring an {@link ComponentElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ComponentElementDeclarer<E extends ComponentElementDeclarer, D extends ComponentElementDeclaration>
    extends ParameterizedElementDeclarer<E, D>
    implements HasNestedComponentDeclarer<E>, HasNestedRoutesDeclaration<E> {

  ComponentElementDeclarer(D declaration) {
    super(declaration);
  }

  public E withConfig(String configRefName) {
    declaration.setConfigRef(configRefName);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withRoute(RouteElementDeclaration component) {
    component.setDeclaringExtension(declaration.getDeclaringExtension());
    declaration.addComponent(component);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withRoute(String name, Consumer<RouteElementDeclarer> enricher) {
    final RouteElementDeclarer declarer =
        new RouteElementDeclarer(new RouteElementDeclaration(declaration.getDeclaringExtension(), name));
    enricher.accept(declarer);
    declaration.addComponent(declarer.getDeclaration());
    return (E) this;
  }

}
