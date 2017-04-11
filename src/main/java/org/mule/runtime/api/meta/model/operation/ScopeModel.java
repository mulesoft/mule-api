/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.operation;

import org.mule.runtime.api.meta.model.util.ComponentModelVisitor;

/**
 * An {@link OperationModel} specification which represents the particular case of a
 * scope, such as async or foreach.
 * <p>
 *
 * @since 1.0
 */
public interface ScopeModel extends OperationModel {

  /**
   * {@inheritDoc}
   */
  @Override
  default void accept(ComponentModelVisitor visitor) {
    visitor.visit(this);
  }
}
