/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.construct.ConstructModel;
import org.mule.runtime.api.meta.model.nested.NestedChainModel;
import org.mule.runtime.api.meta.model.nested.NestedComponentModel;
import org.mule.runtime.api.meta.model.nested.NestedRouteModel;
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

  /**
   * Visits a {@link NestedComponentModel}
   *
   * @param model the visited object
   * @since 1.4.0
   */
  void visit(NestedComponentModel model);

  /**
   * Visits a {@link NestedChainModel}
   *
   * @param model the visited object
   * @since 1.4.0
   */
  void visit(NestedChainModel model);

  /**
   * Visits a {@link NestedRouteModel}
   *
   * @param model the visited object
   * @since 1.4.0
   */
  void visit(NestedRouteModel model);
}
