/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata.resolving;

import static java.util.Collections.emptyList;

import java.util.List;

/**
 * Immutable implementation of {@link MetadataResult} for successful results.
 *
 * @since 1.0
 */
public final class SuccessMetadataResult<T> implements MetadataResult<T> {

  private final T result;

  SuccessMetadataResult(T result) {
    this.result = result;
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
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @return an empty list.
   */
  @Override
  public List<MetadataFailure> getFailures() {
    return emptyList();
  }
}
