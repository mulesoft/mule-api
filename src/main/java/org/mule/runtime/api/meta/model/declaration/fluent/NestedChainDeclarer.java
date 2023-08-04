/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Allows configuring a {@link NestedComponentDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class NestedChainDeclarer extends NestedComponentDeclarer<NestedChainDeclarer, NestedChainDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link NestedComponentDeclaration} to be configured
   */
  public NestedChainDeclarer(NestedChainDeclaration declaration) {
    super(declaration);
  }

  public NestedChainDeclarer setRequired(boolean required) {
    declaration.setRequired(required);
    return this;
  }

}
