/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;

/**
 * Represents a {@link NestableElementModel} which can be declared as a repeatable element, so multiple declarations of
 * {@code this} {@link NestedRouteModel route} are associated to the same model definition.
 *
 * @since 1.0
 */
@NoImplement
public interface NestedRouteModel extends NestableElementModel {

  /**
   * Represents the minimum amount of times that this route can be used inside the owning component.
   *
   * @return An int greater or equal to zero
   */
  int getMinOccurs();

  /**
   * {@link Optional} value which represents the maximum amount of times that this route can be used inside the owning component.
   *
   * @return If present, a number greater or equal to zero.
   */
  Optional<Integer> getMaxOccurs();

}
