/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.values;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.Set;

/**
 * Immutable implementation of {@link ValueResult}
 *
 * @since 1.0
 */
final class ImmutableValueResult implements ValueResult {

  private Set<Value> values = emptySet();
  private ResolvingFailure failure;

  /**
   * Creates a new instance with a successful result containing the resolved {@link Set} of {@link Value values}.
   *
   * @param values The resolved {@link Set} of {@link Value values}
   */
  ImmutableValueResult(Set<Value> values) {
    this.values = values;
  }

  /**
   * Creates a new instance with a failure result containing the associated {@link ResolvingFailure failure}
   *
   * @param failure The generated failure occurred trying to resolve the {@link Value values}
   */
  ImmutableValueResult(ResolvingFailure failure) {
    this.failure = failure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Value> getValues() {
    return values;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ResolvingFailure> getFailure() {
    return ofNullable(failure);
  }

}
