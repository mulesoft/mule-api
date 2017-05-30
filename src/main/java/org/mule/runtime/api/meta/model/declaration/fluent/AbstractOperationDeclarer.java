/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ExecutionType;
import org.mule.runtime.api.meta.model.error.ErrorModel;

/**
 * Base class for {@link Declarer declarers} which allow to construct an {@link OperationDeclaration}
 *
 * @param <T> the generic type of the concrete declarer
 * @param <D> the generic type of the produced {@link OperationDeclaration}
 * @since 1.0
 */
abstract class AbstractOperationDeclarer<T extends AbstractOperationDeclarer, D extends OperationDeclaration>
    extends ComponentDeclarer<T, D> {

  /**
   * {@inheritDoc}
   */
  public AbstractOperationDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds an {@link ErrorModel} to indicate that the current operation could throw the added error.
   *
   * @param error {@link ErrorModel} to add to the {@link OperationDeclaration}
   * @return {@code this} declarer
   */
  public T withErrorModel(ErrorModel error) {
    declaration.addErrorModel(error);
    return (T) this;
  }

  /**
   * Specifies the operation's {@link ExecutionType}
   *
   * @param executionType the execution type
   * @return {@code this} declarer
   */
  public T withExecutionType(ExecutionType executionType) {
    declaration.setExecutionType(executionType);
    return (T) this;
  }

  /**
   * Specifies if the operation is blocking or it allows non blocking execution
   *
   * @param blocking whether the operation is blocking or not
   * @return {@code this} declarer
   */
  public T blocking(boolean blocking) {
    declaration.setBlocking(blocking);
    return (T) this;
  }
}
