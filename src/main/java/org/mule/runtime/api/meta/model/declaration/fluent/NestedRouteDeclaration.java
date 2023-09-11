/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.NestedRouteModel;

/**
 * A declaration object for a {@link NestedRouteModel}. It contains raw, unvalidated data which is used to declare the structure
 * of a {@link NestedRouteModel}
 *
 * @since 1.0
 */
public class NestedRouteDeclaration extends NestableElementDeclaration<NestedRouteDeclaration>
    implements WithParametersDeclaration, WithNestedComponentsDeclaration<NestedRouteDeclaration> {

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  NestedRouteDeclaration(String name) {
    super(name);
  }
}
