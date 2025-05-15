/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.model.nested.ChainExecutionOccurrence.UNKNOWN;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.runtime.api.meta.model.nested.ChainExecutionOccurrence;
import org.mule.runtime.api.meta.model.nested.NestedChainModel;

/**
 * A declaration object for a {@link NestedChainModel}. It contains raw, unvalidated data which is used to declare the structure
 * of a {@link NestedChainModel}
 *
 * @since 1.0
 */
public class NestedChainDeclaration extends NestedComponentDeclaration<NestedChainDeclaration> {

  private ChainExecutionOccurrence occurrence = UNKNOWN;

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  NestedChainDeclaration(String name) {
    super(name);
    setRequired(true);
  }

  /**
   * @return the chain's {@link ChainExecutionOccurrence}
   * @since 1.7.0
   */
  public ChainExecutionOccurrence getOccurrence() {
    return occurrence;
  }

  /**
   * Sets the chain's {@link ChainExecutionOccurrence}
   *
   * @param occurrence an occurrence. Cannot be {@code null}
   * @since 1.7.0
   */
  public void setOccurrence(ChainExecutionOccurrence occurrence) {
    checkArgument(occurrence != null, "occurrence cannot be null");
    this.occurrence = occurrence;
  }
}
