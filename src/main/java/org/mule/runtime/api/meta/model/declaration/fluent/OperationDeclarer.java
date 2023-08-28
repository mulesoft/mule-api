/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.operation.ExecutionType;

/**
 * Allows configuring a {@link OperationDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class OperationDeclarer extends ExecutableComponentDeclarer<OperationDeclarer, OperationDeclaration> {

  /**
   * Creates a new instance
   *
   * @param declaration the {@link OperationDeclaration} which will be configured
   */
  OperationDeclarer(OperationDeclaration declaration) {
    super(declaration);
  }

  /**
   * Specifies the operation's {@link ExecutionType}
   *
   * @param executionType the execution type
   * @return {@code this} declarer
   */
  public OperationDeclarer withExecutionType(ExecutionType executionType) {
    declaration.setExecutionType(executionType);
    return this;
  }

  /**
   * Specifies if the operation is blocking or it allows non blocking execution
   *
   * @param blocking whether the operation is blocking or not
   * @return {@code this} declarer
   */
  public OperationDeclarer blocking(boolean blocking) {
    declaration.setBlocking(blocking);
    return this;
  }
}
