/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A declaration object for a {@link ComponentModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link ComponentModel}
 *
 * @since 1.0
 */
public class ComponentDeclaration<T extends ComponentDeclaration> extends ParameterizedDeclaration<T>
    implements WithNestedComponentsDeclaration<T> {


  private Set<StereotypeModel> stereotypes = new HashSet<>();
  private List<NestableElementDeclaration> nestedComponents = new LinkedList<>();

  /**
   * {@inheritDoc}
   */
  ComponentDeclaration(String name) {
    super(name);
  }

  public List<NestableElementDeclaration> getNestedComponents() {
    return nestedComponents;
  }

  public T addNestedComponent(NestableElementDeclaration nestedComponentDeclaration) {
    nestedComponents.add(nestedComponentDeclaration);
    return (T) this;
  }

  public Set<StereotypeModel> getStereotypes() {
    return stereotypes;
  }

  public void addStereotype(StereotypeModel stereotype) {
    stereotypes.add(stereotype);
  }

}
