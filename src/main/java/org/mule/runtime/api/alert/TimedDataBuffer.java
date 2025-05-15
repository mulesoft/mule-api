/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.alert;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

import org.mule.api.annotation.Experimental;
import org.mule.runtime.api.time.TimeSupplier;
import org.mule.runtime.internal.alert.TimedData;

import java.time.Instant;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Data structure that stores data points for different moments in time.
 * <p>
 * Data is kept at least for 60 minutes. Items that were added more than 60 minutes ago will be eventually removed.
 *
 * @param <T> the type of the data to be stored in this buffer.
 */
@Experimental
public class TimedDataBuffer<T> {

  private final Supplier<Instant> instantSupplier;

  private Deque<TimedData<T>> buffer = new ConcurrentLinkedDeque<>();

  public TimedDataBuffer() {
    this.instantSupplier = () -> now();
  }

  /**
   * Creates a new timed buffer.
   *
   * @param timeSupplier the supplier of the timestamps to associate with the added data.
   */
  public TimedDataBuffer(TimeSupplier timeSupplier) {
    this.instantSupplier = timeSupplier::getAsInstant;
  }

  /**
   * Adds a data point for the current instant in time.
   * <p>
   * This will also remove entries older than 60 minutes.
   *
   * @param data the data to add for this instant.
   */
  public void put(T data) {
    final Instant now = instantSupplier.get();

    evictOldEntries(now);

    buffer.addFirst(new TimedData<>(now, data));
  }

  /**
   * This will also remove entries older than 60 minutes.
   *
   * @return the number of elements within the 60 minute interval.
   */
  public int size() {
    evictOldEntries(instantSupplier.get());

    return buffer.size();
  }

  /**
   * Aggregates the currently stored data using the provided {@code accumulator} for the time intervals of the last minute, 5
   * minutes, 15 minutes and 60 minutes.
   * <p>
   * The accumulator will be called once for every item on each interval it is present. The order of the invocations will be the
   * same as the insertion order.
   * <p>
   * If there is no data stored, the aggregations will be {@code baseIntevalAggregation}.
   * <p>
   * This will also remove entries older than 60 minutes.
   *
   * @param <A>                    the type of the aggregation result.
   * @param baseIntevalAggregation an empty aggregation.
   * @param accumulator            function to be called for each item, on the current aggregation result and the item itself.
   * @return the aggregations for the time intervals.
   */
  public <A> TimedDataAggregation<A> aggregate(A baseIntevalAggregation,
                                               BiFunction<A, T, A> accumulator) {
    final Instant now = instantSupplier.get();

    evictOldEntries(now);

    A agg60 = baseIntevalAggregation;
    A agg15 = baseIntevalAggregation;
    A agg5 = baseIntevalAggregation;
    A agg1 = baseIntevalAggregation;

    final Instant mark15 = now.minus(15, MINUTES);
    final Instant mark5 = now.minus(5, MINUTES);
    final Instant mark1 = now.minus(1, MINUTES);

    for (Iterator<TimedData<T>> it = buffer.descendingIterator(); it.hasNext();) {
      final TimedData<T> next = it.next();

      agg60 = accumulator.apply(agg60, next.getData());
      if (next.getTime().isAfter(mark15)) {
        agg15 = accumulator.apply(agg15, next.getData());
      }
      if (next.getTime().isAfter(mark5)) {
        agg5 = accumulator.apply(agg5, next.getData());
      }
      if (next.getTime().isAfter(mark1)) {
        agg1 = accumulator.apply(agg1, next.getData());
      }
    }

    return new TimedDataAggregation<>(now, agg1, agg5, agg15, agg60);
  }

  private void evictOldEntries(final Instant now) {
    final Instant timeLimit = now.minus(60, MINUTES);
    TimedData<T> last = buffer.peekLast();
    while (last != null && last.getTime().isBefore(timeLimit)) {
      buffer.removeLast();
      last = buffer.peekLast();
    }
  }

}
