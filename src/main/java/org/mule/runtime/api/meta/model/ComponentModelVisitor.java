/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.construct.ConstructModel;
import org.mule.runtime.api.meta.model.operation.OperationModel;
import org.mule.runtime.api.meta.model.source.SourceModel;

/**
 * Visitor interface for traversing a {@link ComponentModel} hierarchy
 *
 * @since 1.0
 */
@NoImplement
public interface ComponentModelVisitor {

  /**
   * Visits an {@link OperationModel}
   *
   * @param model the visited object
   */
  void visit(OperationModel model);

  /**
   * Visits an {@link SourceModel}
   *
   * @param model the visited object
   */
  void visit(SourceModel model);

  /**
   * Visits a {@link ConstructModel}
   *
   * @param model the visited object
   */
  void visit(ConstructModel model);
}
