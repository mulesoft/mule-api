/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * A declaration object for a {@link SourceModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link SourceModel}
 *
 * @since 1.0
 */
public class SourceDeclaration extends ComponentDeclaration<SourceDeclaration> {

  private boolean hasResponse = false;

  /**
   * {@inheritDoc}
   */
  SourceDeclaration(String name) {
    super(name);
  }

  /**
   * @return Whether the declared source emits a response
   */
  public boolean hasResponse() {
    return hasResponse;
  }

  /**
   * @param hasResponse Whether the declared source emits a response
   */
  public void setHasResponse(boolean hasResponse) {
    this.hasResponse = hasResponse;
  }
}
