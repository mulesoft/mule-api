/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComposableModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;

import java.util.Optional;

/**
 * Represents a {@link NestableElementModel} that is itself a {@link ComposableModel} and {@link ParameterizedModel}.
 * A {@link NestedRouteModel route} can be declared as a repeatable element, so multiple declarations of {@code this}
 * {@link NestedRouteModel route} are associated to the same model definition.
 *
 * @since 1.0
 */
@NoImplement
public interface NestedRouteModel extends NestableElementModel, ParameterizedModel, ComposableModel {

  /**
   * Represents the minimum amount of times that this route can be used inside the owning component.
   *
   * @return An int greater or equal to zero
   */
  int getMinOccurs();

  /**
   * {@link Optional} value which represents the maximum amount of times that this route can be used inside the owning
   * component.
   *
   * @return If present, a number greater or equal to zero.
   */
  Optional<Integer> getMaxOccurs();

}
