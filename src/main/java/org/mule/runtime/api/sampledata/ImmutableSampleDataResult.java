/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.sampledata;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

import org.mule.runtime.api.message.Message;

import java.util.Optional;

/**
 * Immutable implementation of {@link SampleDataResult}
 *
 * @since 1.4
 */
public class ImmutableSampleDataResult implements SampleDataResult {

  private SampleDataFailure failure;
  private Message message;

  public ImmutableSampleDataResult(SampleDataFailure failure) {
    requireNonNull(failure, "failure cannot be bull");
    this.failure = failure;
  }

  public ImmutableSampleDataResult(Message message) {
    requireNonNull(message, "message cannot be bull");
    this.message = message;
  }

  @Override
  public Optional<Message> getSampleData() {
    return ofNullable(message);
  }

  @Override
  public Optional<SampleDataFailure> getFailure() {
    return ofNullable(failure);
  }
}
