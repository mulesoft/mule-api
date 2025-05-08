/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.alert;

/**
 * Provides the results of the aggregation of a given data type for time intervals of 1, 5 and 15 minutes.
 * 
 * @param <A> the type of the aggregation result.
 * 
 * @since 1.10
 */
public final class TimedDataAggregation<A> {

  private A agg1;
  private A agg5;
  private A agg15;
  private A agg60;

  TimedDataAggregation(A agg1, A agg5, A agg15, A agg60) {
    this.agg1 = agg1;
    this.agg5 = agg5;
    this.agg15 = agg15;
    this.agg60 = agg60;
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
