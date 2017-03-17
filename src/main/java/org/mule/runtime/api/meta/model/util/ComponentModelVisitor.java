/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.util;

import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.operation.RouterModel;
import org.mule.runtime.api.meta.model.operation.ScopeModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * Visitor interface for traversing a {@link ComponentModel} hierarchy
 *
 * @since 1.0
 */
public interface ComponentModelVisitor {

  /**
   * Visits an {@link OperationModel}
   *
   * @param operationModel the visited object
   */
  default void visit(OperationModel operationModel){}

  /**
   * Visits an {@link ScopeModel}
   *
   * @param scopeModel the visited object
   */
  default void visit(ScopeModel scopeModel){}

  /**
   * Visits an {@link RouterModel}
   *
   * @param routerModel the visited object
   */
  default void visit(RouterModel routerModel){}

  /**
   * Visits an {@link SourceModel}
   *
   * @param sourceModel the visited object
   */
  default void visit(SourceModel sourceModel){}
}
