/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.NestedChainModel;

/**
 * A declaration object for a {@link NestedChainModel}. It contains raw, unvalidated data which is used to declare the structure
 * of a {@link NestedChainModel}
 *
 * @since 1.0
 */
public class NestedChainDeclaration extends NestedComponentDeclaration<NestedChainDeclaration> {

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  NestedChainDeclaration(String name) {
    super(name);
    setRequired(true);
  }

}
