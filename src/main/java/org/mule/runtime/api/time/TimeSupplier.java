/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.time;

import static java.time.Instant.ofEpochMilli;

import org.mule.api.annotation.NoImplement;

import java.time.Instant;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * A {@link Supplier} which provides a unified time in milliseconds.
 *
 * @since 4.0
 */
@NoImplement
public interface TimeSupplier extends Supplier<Long>, LongSupplier {

  /**
   * Returns {@link System#currentTimeMillis()}
   *
   * @return the current time in milliseconds
   *
   * @deprecated use {@link #getAsLong()} instead.
   */
  @Override
  @Deprecated
  Long get();

  /**
   * Returns {@link System#currentTimeMillis()}
   *
   * @return the current time in milliseconds
   */
  @Override
  long getAsLong();

  /**
   * Returns {@link Instant#now()}
   *
   * @return the current instant
   * @since 1.10
   */
  default Instant getAsInstant() {
    return ofEpochMilli(getAsLong());
  }
  
}
