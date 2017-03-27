/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.meta.model.operation.RouteModel;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a {@link RouteModel} configuration.
 *
 * @since 1.0
 */
//TODO MULE-12061: delete once RouteModel is removed
public final class RouteElementDeclaration extends ParameterizedElementDeclaration {

  private List<ComponentElementDeclaration> components = new LinkedList<>();

  public RouteElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} associated with
   * {@code this} {@link FlowElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  public RouteElementDeclaration addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return this;
  }

}
