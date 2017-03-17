/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of an application Flow configuration.
 *
 * @since 1.0
 */
public final class FlowElementDeclaration extends ParameterizedElementDeclaration {

  private List<ComponentElementDeclaration> components = new LinkedList<>();

  public FlowElementDeclaration() {}

  public FlowElementDeclaration(String name) {
    setDeclaringExtension("Mule Core");
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} associated with
   * {@code this} {@link FlowElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  public FlowElementDeclaration addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FlowElementDeclaration) || !super.equals(o)) {
      return false;
    }

    FlowElementDeclaration that = (FlowElementDeclaration) o;
    return components.equals(that.components);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + components.hashCode();
    return result;
  }
}
