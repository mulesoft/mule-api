/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Collections.unmodifiableSet;
import org.mule.runtime.api.meta.model.nested.NestedComponentModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.HashSet;
import java.util.Set;

/**
 * A declaration object for a {@link NestedComponentModel}. It contains raw, unvalidated data which is used to declare the
 * structure of a {@link NestedComponentModel}
 *
 * @since 1.0
 */
public class NestedComponentDeclaration<T extends NestedComponentDeclaration> extends NestableElementDeclaration<T>
    implements WithAllowedStereotypesDeclaration<T> {

  private final Set<StereotypeModel> stereotypes = new HashSet<>();

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  NestedComponentDeclaration(String name) {
    super(name);
  }

  @Override
  public Set<StereotypeModel> getAllowedStereotypes() {
    return unmodifiableSet(stereotypes);
  }

  @Override
  public T addAllowedStereotype(StereotypeModel stereotype) {
    stereotypes.add(stereotype);
    return (T) this;
  }
}
