/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

/**
 * A programmatic descriptor of an application Flow configuration.
 *
 * @since 1.0
 */
public final class FlowElementDeclaration extends ScopeElementDeclaration implements GlobalElementDeclaration {

  private static final String CE_EXTENSION_NAME = "Mule Core";

  public FlowElementDeclaration() {}

  public FlowElementDeclaration(String name) {
    setDeclaringExtension(CE_EXTENSION_NAME);
    setName(name);
  }

  @Override
  public void accept(GlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }
}
