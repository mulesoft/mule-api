/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.time;

import org.mule.api.annotation.NoImplement;

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
}
