/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

/**
 * Used in {@link GlobalElementDeclaration#accept(GlobalElementDeclarationVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
public interface GlobalElementDeclarationVisitor {

  default void visit(ConfigurationElementDeclaration declaration) {
    // do nothing
  }

  default void visit(TopLevelParameterDeclaration declaration) {
    // do nothing
  }

  default void visit(FlowElementDeclaration declaration) {
    // do nothing
  }
}
