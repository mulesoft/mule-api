/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.value;

import org.mule.api.annotation.NoImplement;

import java.util.Optional;
import java.util.Set;

/**
 * Represents the result of the resolution of the possible values of a element capable of resolve {@link Value values}.
 *
 * @since 1.0
 */
@NoImplement
public interface ValueResult {

  /**
   * Creates a new {@link ValueResult} with a successful result containing the resolved {@link Set} of {@link Value values}.
   *
   * @param values The resolved {@link Set} of {@link Value values}
   * @return a {@link ValueResult} with the resolved values.
   */
  static ValueResult resultFrom(Set<Value> values) {
    return new ImmutableValueResult(values);
  }

  /**
   * Creates a new {@link ValueResult} with a failure result containing the associated {@link ResolvingFailure failure}
   *
   * @param failure The generated failure occurred trying to resolve the {@link Value values}
   * @return a {@link ValueResult} with the {@link ResolvingFailure}
   */
  static ValueResult resultFrom(ResolvingFailure failure) {
    return new ImmutableValueResult(failure);
  }

  /**
   * @return The resolved {@link Set} of {@link Value values}. In an error case this will return an empty {@link Set}.
   */
  Set<Value> getValues();

  /**
   * @return An {@link Optional} {@link ResolvingFailure failure} with the error that occurred trying to resolve the {@link Value
   *         values}
   */
  Optional<ResolvingFailure> getFailure();

  /**
   * @return A boolean indicating if the resolution finished correctly or not.
   */
  default boolean isSuccess() {
    return !getFailure().isPresent();
  }
}
