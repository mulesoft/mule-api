/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

/**
 * Visitor interface for traversing a {@link NestableElementModel} hierarchy
 *
 * @since 1.0
 */
public interface NestableElementModelVisitor {

  default void visit(NestedComponentModel component) {}

  default void visit(NestedChainModel component) {}

  default void visit(NestedRouteModel component) {}

}
