/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.util;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.util.LazyLong;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class LazyLongTestCase {

  private LazyLong lazy;

  @Test
  public void computeOnlyOnce() {
    AtomicInteger count = new AtomicInteger(0);
    lazy = new LazyLong(() -> {
      count.incrementAndGet();
      return 8L;
    });

    long value = lazy.getAsLong();
    assertThat(lazy.getAsLong(), is(value));
    assertThat(count.get(), is(1));
  }

  @Test
  public void isInitialised() {
    lazy = new LazyLong(() -> 8L);
    assertThat(lazy.isComputed(), is(false));
    lazy.getAsLong();
    assertThat(lazy.isComputed(), is(true));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSupplier() {
    new LazyLong(null);
  }

  @Test
  public void concurrentInitialisation() throws Exception {
    CountDownLatch latch1 = new CountDownLatch(1);
    CountDownLatch latch2 = new CountDownLatch(1);

    final long value = 8;
    final AtomicInteger invocationCount = new AtomicInteger(0);
    final AtomicReference<Object> value1 = new AtomicReference<>(null);
    final AtomicReference<Object> value2 = new AtomicReference<>(null);

    LazyLong lazy = new LazyLong(() -> {
      invocationCount.addAndGet(1);
      return value;
    });

    Thread thread1 = new Thread(() -> {
      latch1.countDown();
      await(latch2);
      value1.set(lazy.getAsLong());
    });

    Thread thread2 = new Thread(() -> {
      await(latch1);
      latch2.countDown();
      value2.set(lazy.getAsLong());
    });

    thread1.start();
    thread2.start();

    thread1.join(1000);
    thread2.join(1000);

    assertThat(invocationCount.get(), is(1));
    assertThat(value1.get(), is(sameInstance(value2.get())));
    assertThat(value1.get(), is(sameInstance(value)));
  }

  private void await(CountDownLatch latch) {
    try {
      latch.await(1, SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
