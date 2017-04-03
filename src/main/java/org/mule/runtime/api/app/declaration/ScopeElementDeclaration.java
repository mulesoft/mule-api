/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.meta.model.operation.ScopeModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a {@link ScopeModel} configuration.
 *
 * @since 1.0
 */
public class ScopeElementDeclaration extends ComponentElementDeclaration {

  private List<ComponentElementDeclaration> components = new LinkedList<>();

  public ScopeElementDeclaration() {}

  public ScopeElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} contained by
   * {@code this} {@link ScopeElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  /**
   * Adds a {@link ComponentElementDeclaration} as a component contained by {@code this} {@link ScopeElementDeclaration scope}
   * @param declaration the {@link ComponentElementDeclaration} child of {@code this} {@link ScopeElementDeclaration scope}
   * @return {@code this} {@link ScopeElementDeclaration scope}
   */
  public ScopeElementDeclaration addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass() || !super.equals(o)) {
      return false;
    }

    ScopeElementDeclaration that = (ScopeElementDeclaration) o;
    return components.equals(that.components);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + components.hashCode();
    return result;
  }

}
