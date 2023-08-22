/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.metadata.resolving;

import static java.util.Arrays.asList;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Container for the Metadata fetch operations provided by {@link TypeKeysResolver}, {@link InputTypeResolver} and
 * {@link OutputTypeResolver} results. Allows to communicate errors without propagating exceptions to the Metadata fetching
 * service
 *
 * @param <T> return type of the Metadata resolving operation.
 * @since 1.0
 */
@NoImplement
public interface MetadataResult<T> {

  /**
   * Creates a success {@link MetadataResult}.
   *
   * @param payload object returned by the metadata operation
   * @return a {@link SuccessMetadataResult} instance
   */
  static <T> MetadataResult<T> success(T payload) {
    return new SuccessMetadataResult<>(payload);
  }

  /**
   * Creates a failure {@link MetadataResult} with a payload and with one or more associated {@link MetadataFailure}s.
   *
   * @param result   the resulting content bounded to this failure result.
   * @param failures one or more {@link MetadataFailure}s that contains the failure information.
   * @return a {@link FailureMetadataResult} instance.
   */
  static <T> MetadataResult<T> failure(T result, MetadataFailure... failures) {
    return new FailureMetadataResult<>(result, asList(failures));
  }

  /**
   * Creates a failure {@link MetadataResult} with a payload and a list of associated {@link MetadataFailure}s.
   *
   * @param result   the resulting content bounded to this failure result.
   * @param failures a list of {@link MetadataFailure}s that contains the failure information.
   * @return a {@link FailureMetadataResult} instance.
   */
  static <T> MetadataResult<T> failure(T result, List<MetadataFailure> failures) {
    return new FailureMetadataResult<>(result, failures);
  }

  /**
   * Creates a failure {@link MetadataResult} with one or more associated {@link MetadataFailure}s.
   *
   * @param failures one or more {@link MetadataFailure}s that contains the failure information.
   * @return a {@link FailureMetadataResult} instance.
   */
  static <T> MetadataResult<T> failure(MetadataFailure... failures) {
    return new FailureMetadataResult<>(null, asList(failures));
  }

  /**
   * Creates a failure {@link MetadataResult} with a list of associated {@link MetadataFailure}s.
   *
   * @param failures a list of {@link MetadataFailure}s that contains the failure information.
   * @return a {@link FailureMetadataResult} instance.
   */
  static <T> MetadataResult<T> failure(List<MetadataFailure> failures) {
    return new FailureMetadataResult<>(null, failures);
  }

  /**
   * @return the object returned by the invoked Metadata operation
   */
  T get();

  /**
   * @return true if the operation was completed without errors, false otherwise
   */
  boolean isSuccess();

  /**
   * If {@link this#isSuccess} is false, then a {@link MetadataFailure} instance is provided in order to describe the error that
   * occurred during the invocation.
   *
   * @return a {@link List} of {@link MetadataFailure}s describing the errors that occurred during the invocation if at least one
   *         occurred.
   */
  List<MetadataFailure> getFailures();

}
