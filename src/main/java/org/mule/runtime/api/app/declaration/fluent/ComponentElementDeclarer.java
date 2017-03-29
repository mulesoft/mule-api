/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;

/**
 * Allows configuring an {@link ComponentElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ComponentElementDeclarer<D extends ComponentElementDeclarer, T extends ComponentElementDeclaration>
    extends ParameterizedElementDeclarer<D, T> {

  ComponentElementDeclarer(T declaration) {
    super(declaration);
  }

  public D withConfig(String configRefName) {
    declaration.setConfigRef(configRefName);
    return (D) this;
  }
}
