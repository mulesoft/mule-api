/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
