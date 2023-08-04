/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.time;

import java.util.concurrent.TimeUnit;

/**
 * Represents a scalar amount of time expressed in a certain {@link TimeUnit}
 *
 * @since 1.0
 */
public final class Time {

  private final long time;
  private final TimeUnit timeUnit;

  /**
   * Creates a new instance
   *
   * @param time     a scalar value representing a time
   * @param timeUnit a {@link TimeUnit} that qualifies the {@code time}
   */
  public Time(long time, TimeUnit timeUnit) {
    this.time = time;
    this.timeUnit = timeUnit;
  }

  /**
   * Returns a scalar time value
   *
   * @return a scalar time value as a native {@link long}
   */
  public long getTime() {
    return time;
  }

  /**
   * A {@link TimeUnit} which qualifies {@link #getTime()}
   *
   * @return a {@link TimeUnit}
   */
  public TimeUnit getUnit() {
    return timeUnit;
  }
}
