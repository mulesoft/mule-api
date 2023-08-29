/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ComponentModel;

/**
 * A declaration object for a {@link ComponentModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link ComponentModel}
 *
 * @since 1.0
 */
public class ConstructDeclaration extends ComponentDeclaration<ConstructDeclaration> {

  private boolean allowsTopLevelDefinition = false;

  /**
   * {@inheritDoc}
   */
  ConstructDeclaration(String name) {
    super(name);
  }

  public void setAllowsTopLevelDefinition(boolean allowsTopLevelDefinition) {
    this.allowsTopLevelDefinition = allowsTopLevelDefinition;
  }

  public boolean allowsTopLevelDefinition() {
    return allowsTopLevelDefinition;
  }
}
