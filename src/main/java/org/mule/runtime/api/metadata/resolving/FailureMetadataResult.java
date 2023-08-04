/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

import static java.util.Collections.unmodifiableList;

import java.util.List;

/**
 * Immutable implementation of {@link MetadataResult} for failure results.
 *
 * @since 1.0
 */
public final class FailureMetadataResult<T> implements MetadataResult<T> {

  private final T result;
  private final List<MetadataFailure> failures;

  FailureMetadataResult(T result, List<MetadataFailure> failures) {
    if (failures == null || failures.isEmpty()) {
      throw new IllegalArgumentException("metadata failures can't be null or empty");
    }
    this.result = result;
    this.failures = unmodifiableList(failures);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T get() {
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSuccess() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<MetadataFailure> getFailures() {
    return failures;
  }
}
