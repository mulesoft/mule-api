/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ConstructElementDeclaration;

/**
 * Allows configuring a {@link ConstructElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConstructElementDeclarer
    extends ComponentElementDeclarer<ConstructElementDeclarer, ConstructElementDeclaration> {

  /**
   * Creates a new instance of {@link ConstructElementDeclarer}
   */
  ConstructElementDeclarer(ConstructElementDeclaration declaration) {
    super(declaration);
  }

  public ConstructElementDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

}
