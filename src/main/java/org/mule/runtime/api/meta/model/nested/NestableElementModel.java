/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.ComposableModel;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;

/**
 * A definition of an element that can be contained by a {@link ComposableModel}.
 *
 * @see NestedComponentModel
 * @see NestedRouteModel
 * @since 1.0
 */
@NoImplement
public interface NestableElementModel extends NamedObject, EnrichableModel, DescribedObject, HasDisplayModel {

  /**
   * @return whether or not {@code this} element is required for its owner element
   */
  boolean isRequired();

  /**
   * Accepts a {@link NestableElementModelVisitor}
   *
   * @param visitor a {@link NestableElementModelVisitor}
   */
  void accept(NestableElementModelVisitor visitor);
}
