/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.NestedChainModel;

/**
 * A declaration object for a {@link NestedChainModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link NestedChainModel}
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
