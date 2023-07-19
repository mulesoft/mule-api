/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.nested;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.ComponentModelVisitor;
import org.mule.runtime.api.meta.model.ComposableModel;

import java.util.Optional;

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
   * Represents the minimum amount of times that this component can be used inside the owning one.
   *
   * @return An int greater or equal to zero
   * @since 1.4.0
   */
  int getMinOccurs();

  /**
   * {@link Optional} value which represents the maximum amount of times that this component can be used inside the owning one.
   * {@link Optional#empty()} means unbounded.
   *
   * @return If present, a number greater or equal than zero.
   * @since 1.4.0
   */
  Optional<Integer> getMaxOccurs();

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
