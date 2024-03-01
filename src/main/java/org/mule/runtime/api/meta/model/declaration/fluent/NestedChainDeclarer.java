/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.ChainExecutionOccurrence;

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

  /**
   * Sets the chain's {@link ChainExecutionOccurrence}
   * 
   * @param occurrence an occurrence. Cannot be {@code null}
   * @return {@code this} declarer
   */
  public NestedChainDeclarer setExecutionOccurrence(ChainExecutionOccurrence occurrence) {
    declaration.setOccurrence(occurrence);
    return this;
  }

}
