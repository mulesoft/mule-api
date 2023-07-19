/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.runtime.api.meta.model.ComponentModelVisitor;

/**
 * Visitor interface for traversing a {@link NestableElementModel} hierarchy
 *
 * @since 1.0
 * @deprecated since 1.4.0. Use {@link ComponentModelVisitor} instead
 */
@Deprecated
public interface NestableElementModelVisitor {

  default void visit(NestedComponentModel component) {}

  default void visit(NestedChainModel component) {}

  default void visit(NestedRouteModel component) {}

}
