/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
