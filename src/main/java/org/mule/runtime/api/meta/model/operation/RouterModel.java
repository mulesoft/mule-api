/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.operation;

import org.mule.runtime.api.meta.model.util.ComponentModelVisitor;

import java.util.List;

/**
 * An {@link OperationModel} specification which represents the particular case of a
 * router, such as choice, scatter-gather, first-successful, etc.
 * <p>
 * It adds a {@link #getRouteModels() list of routes} which describe the contained routes.
 *
 * @since 1.0
 */
public interface RouterModel extends OperationModel {

  /**
   * Returns the list of {@link RouteModel routes} that this router contains.
   * @return A non empty {@link List}
   */
  List<RouteModel> getRouteModels();

  /**
   * {@inheritDoc}
   */
  @Override
  default void accept(ComponentModelVisitor visitor) {
    visitor.visit(this);
  }
}
