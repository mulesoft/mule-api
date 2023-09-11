/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.sampledata;

import org.mule.runtime.api.message.Message;

import java.util.Optional;

/**
 * Represents the result of a sample data connector request
 *
 * @since 1.4
 */
public interface SampleDataResult {

  /**
   * Creates a new {@link SampleDataResult} with a successful result containing the resolved sample data.
   *
   * @param message The resolved {@Message} with the sample data
   * @return a {@link SampleDataResult} with the resolved sample data.
   */
  static SampleDataResult resultFrom(Message message) {
    return new ImmutableSampleDataResult(message);
  }

  /**
   * Creates a new {@link SampleDataResult} with a failure result containing the associated {@link SampleDataFailure failure}
   *
   * @param failure The generated failure occurred trying to resolve the sample data
   * @return a {@link SampleDataResult} with the {@link SampleDataFailure}
   */
  static SampleDataResult resultFrom(SampleDataFailure failure) {
    return new ImmutableSampleDataResult(failure);
  }

  /**
   * @return An {@link Optional} {@link Message} with the sample data. In an error case returns empty.
   */
  Optional<Message> getSampleData();

  /**
   * @return An {@link Optional} {@link SampleDataFailure failure} with the error that occurred trying to resolve the sample data
   */
  Optional<SampleDataFailure> getFailure();

  /**
   * @return A boolean indicating if the resolution finished correctly or not.
   */
  default boolean isSuccess() {
    return !getFailure().isPresent();
  }
}
