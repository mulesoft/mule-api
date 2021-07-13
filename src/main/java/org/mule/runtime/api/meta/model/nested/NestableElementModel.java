/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ComponentModelVisitor;
import org.mule.runtime.api.meta.model.ComposableModel;

/**
 * A definition of an element that can be contained by a {@link ComposableModel}.
 *
 * @deprecated since 1.4.0. Use {@link NestedComponentModel} instead.
 * @see NestedComponentModel
 * @see NestedRouteModel
 * @since 1.0
 */
@NoImplement
@Deprecated
public interface NestableElementModel extends ComponentModel {

  /**
   * @return whether or not {@code this} element is required for its owner element
   */
  boolean isRequired();

  /**
   * Accepts a {@link NestableElementModelVisitor}
   *
   * @param visitor a {@link NestableElementModelVisitor}
   * @deprecated since 1.4.0. Use {@link #accept(ComponentModelVisitor)} instead
   */
  @Deprecated
  void accept(NestableElementModelVisitor visitor);
}
