/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Allows configuring a {@link OperationDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ConstructDeclarer extends ComponentDeclarer<ConstructDeclarer, ConstructDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link ConstructDeclaration} which will be configured
   */
  ConstructDeclarer(ConstructDeclaration declaration) {
    super(declaration);
  }

  /**
   * Declares that {@code this} construct allows to be declared as a root component
   *
   * @return {@code this} declarer
   */
  public ConstructDeclarer allowingTopLevelDefinition() {
    declaration.setAllowsTopLevelDefinition(true);
    return this;
  }

}
