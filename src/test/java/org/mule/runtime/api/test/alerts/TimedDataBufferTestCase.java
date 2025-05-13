/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.alerts;

import static java.time.Instant.ofEpochMilli;
import static java.util.concurrent.TimeUnit.MINUTES;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;

import static org.junit.Assert.fail;

import org.mule.runtime.api.alert.TimedDataAggregation;
import org.mule.runtime.api.alert.TimedDataBuffer;
import org.mule.runtime.api.time.TimeSupplier;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TimedDataBufferTestCase {

  private MutableTimeSupplier mutableTimeSupplier;
  private TimedDataBuffer<String> timedDataBuffer;

  @Before
  public void setUp() {
    mutableTimeSupplier = new MutableTimeSupplier();
    mutableTimeSupplier.setCurrentTimeMillis(0);
    timedDataBuffer = new TimedDataBuffer<>(mutableTimeSupplier);
  }

  @Test
  public void aggregationOnEmpty() {
    TimedDataAggregation aggregation = timedDataBuffer.aggregate("base", (a, t) -> {
      fail("Aggregation function called for no items");
      return a;
    });

    assertThat(aggregation.forLast1MinInterval(), is("base"));
    assertThat(aggregation.forLast5MinsInterval(), is("base"));
    assertThat(aggregation.forLast15MinsInterval(), is("base"));
  }

  @Test
  public void oneEntryEachInterval() {
    timedDataBuffer.put("a");
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(2));
    timedDataBuffer.put("b");
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(6));
    timedDataBuffer.put("c");


    TimedDataAggregation<List<String>> aggregation = timedDataBuffer.aggregate(Collections.<String>emptyList(), (a, t) -> {
      List<String> partialAggregation = new ArrayList<String>();
      partialAggregation.addAll(a);
      partialAggregation.add(t);
      return partialAggregation;
    });

    assertThat(aggregation.forLast1MinInterval(), contains("c"));
    assertThat(aggregation.forLast5MinsInterval(), contains("b", "c"));
    assertThat(aggregation.forLast15MinsInterval(), contains("a", "b", "c"));
  }

  @Test
  public void cleanOnPut() {
    timedDataBuffer.put("a");
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(16));
    timedDataBuffer.put("c");

    TimedDataAggregation<List<String>> aggregation = timedDataBuffer.aggregate(Collections.<String>emptyList(), (a, t) -> {
      List<String> partialAggregation = new ArrayList<String>();
      partialAggregation.addAll(a);
      partialAggregation.add(t);
      return partialAggregation;
    });

    assertThat(aggregation.forLast1MinInterval(), contains("c"));
    assertThat(aggregation.forLast5MinsInterval(), contains("c"));
    assertThat(aggregation.forLast15MinsInterval(), contains("c"));
  }

  @Test
  public void cleanOnAggregate() {
    timedDataBuffer.put("a");
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(16));

    TimedDataAggregation<List<String>> aggregation = timedDataBuffer.aggregate(Collections.<String>emptyList(), (a, t) -> {
      List<String> partialAggregation = new ArrayList<String>();
      partialAggregation.addAll(a);
      partialAggregation.add(t);
      return partialAggregation;
    });

    assertThat(aggregation.forLast1MinInterval(), emptyIterable());
    assertThat(aggregation.forLast5MinsInterval(), emptyIterable());
    assertThat(aggregation.forLast15MinsInterval(), emptyIterable());
  }

  @Test
  public void cleanOnSize() {
    timedDataBuffer.put("a");
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(50));
    timedDataBuffer.put("c");

    assertThat(timedDataBuffer.size(), is(2));
    mutableTimeSupplier.setCurrentTimeMillis(MINUTES.toMillis(70));
    assertThat(timedDataBuffer.size(), is(1));
  }

  private static class MutableTimeSupplier implements TimeSupplier {

    private long currentTimeMillis;

    @Override
    public Long get() {
      return currentTimeMillis;
    }

    @Override
    public long getAsLong() {
      return currentTimeMillis;
    }

    @Override
    public Instant getAsInstant() {
      return ofEpochMilli(currentTimeMillis);
    }

    public void setCurrentTimeMillis(long currentTimeMillis) {
      this.currentTimeMillis = currentTimeMillis;
    }
  }
}
