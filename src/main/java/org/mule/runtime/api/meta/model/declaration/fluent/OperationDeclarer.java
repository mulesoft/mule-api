/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
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
public class OperationDeclarer extends AbstractOperationDeclarer<OperationDeclarer, OperationDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link OperationDeclaration} which will be configured
   */
  OperationDeclarer(OperationDeclaration declaration) {
    super(declaration);
  }
}
