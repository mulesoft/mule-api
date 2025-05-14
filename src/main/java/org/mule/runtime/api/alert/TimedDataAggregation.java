/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.alert;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoInstantiate;

import java.time.Instant;

/**
 * Provides the results of the aggregation of a given data type for time intervals of 1, 5 and 15 minutes.
 * 
 * @param <A> the type of the aggregation result.
 * 
 * @since 1.10
 */
@NoInstantiate
@Experimental
public final class TimedDataAggregation<A> {

  private final Instant creationTime;

  private final A agg1;
  private final A agg5;
  private final A agg15;
  private final A agg60;

  TimedDataAggregation(Instant creationTime, A agg1, A agg5, A agg15, A agg60) {
    this.creationTime = creationTime;

    this.agg1 = agg1;
    this.agg5 = agg5;
    this.agg15 = agg15;
    this.agg60 = agg60;
  }

  /**
   * @return the instant when this aggregation was calculated.
   */
  public Instant getCreationTime() {
    return creationTime;
  }

  /**
   * @return the aggregation result for the last minute.
   */
  public A forLast1MinInterval() {
    return agg1;
  }

  /**
   * @return the aggregation result for the last 5 minutes.
   */
  public A forLast5MinsInterval() {
    return agg5;
  }

  /**
   * @return the aggregation result for the last 15 minutes.
   */
  public A forLast15MinsInterval() {
    return agg15;
  }

  /**
   * @return the aggregation result for the last 60 minutes.
   */
  public A forLast60MinsInterval() {
    return agg60;
  }

  @Override
  public String toString() {
    return agg1 + ", " + agg5 + ", " + agg15 + ", " + agg60;
  }
}
