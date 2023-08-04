/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.value;

import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Immutable implementation of {@link ValueResult}
 *
 * @since 1.0
 */
public final class ImmutableValueResult implements ValueResult {

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

  @Override
  public String toString() {
    return "ImmutableValueResult{" +
        "values=" + values +
        ", failure=" + failure +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ImmutableValueResult that = (ImmutableValueResult) o;

    return new EqualsBuilder()
        .append(values, that.values)
        .append(failure, that.failure)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(values)
        .append(failure)
        .toHashCode();
  }
}
