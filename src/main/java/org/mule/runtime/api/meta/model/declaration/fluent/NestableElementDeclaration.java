/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.nested.NestableElementModel;

/**
 * A declaration object for a {@link NestableElementModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link NestableElementModel}
 *
 * @since 1.0
 */
public class NestableElementDeclaration<T extends NestableElementDeclaration> extends NamedDeclaration<T> {

  private boolean isRequired;

  public boolean isRequired() {
    return isRequired;
  }

  public void setRequired(boolean required) {
    isRequired = required;
  }

  /**
   * Creates a new instance
   *
   * @param name the name of the component being declared
   */
  public NestableElementDeclaration(String name) {
    super(name);
  }
}
