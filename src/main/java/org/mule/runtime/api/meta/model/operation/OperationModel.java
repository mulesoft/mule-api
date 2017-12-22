/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.operation;

import org.mule.runtime.api.meta.model.ComponentModelVisitor;
import org.mule.runtime.api.meta.model.ConnectableComponentModel;
import org.mule.runtime.api.meta.model.ExtensionModel;

/**
 * A definition of an operation in a {@link ExtensionModel}.
 * <p>
 * Operation models implement the flyweight pattern. This means
 * that a given operation should only be represented by only
 * one instance of this class. Thus, if the same operation is
 * contained by different {@link HasOperationModels} instances,
 * then each of those containers should reference the same
 * operation model instance.
 * <p>
 * Do not create custom implementations of this interface. The Mule Runtime should be
 * the only one providing implementations of it.
 *
 * @since 1.0
 */
public interface OperationModel extends ConnectableComponentModel {

  /**
   * @return Whether this operation is blocking or non blocking execution is supported
   */
  boolean isBlocking();

  /**
   * @return this operation's {@link ExecutionType}
   */
  ExecutionType getExecutionType();

  /**
   * {@inheritDoc}
   */
  @Override
  default void accept(ComponentModelVisitor visitor) {
    visitor.visit(this);
  }
}
