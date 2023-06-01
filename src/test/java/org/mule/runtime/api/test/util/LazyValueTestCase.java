/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.util;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.api.util.LazyValue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class LazyValueTestCase {

  private LazyValue<Object> lazy;

  @Test
  public void computeOnlyOnce() {
    lazy = new LazyValue<>(Object::new);
    Object value = lazy.get();
    assertThat(lazy.get(), is(sameInstance(value)));
  }

  @Test
  public void isInitialised() {
    lazy = new LazyValue<>(Object::new);
    assertThat(lazy.isComputed(), is(false));
    lazy.get();
    assertThat(lazy.isComputed(), is(true));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSupplier() {
    new LazyValue<>(null);
  }

  @Test
  public void concurrentInitialisation() throws Exception {
    CountDownLatch latch1 = new CountDownLatch(1);
    CountDownLatch latch2 = new CountDownLatch(1);

    final Object value = new Object();
    final AtomicInteger invocationCount = new AtomicInteger(0);
    final AtomicReference<Object> value1 = new AtomicReference<>(null);
    final AtomicReference<Object> value2 = new AtomicReference<>(null);

    LazyValue<Object> lazy = new LazyValue<>(() -> {
      invocationCount.addAndGet(1);
      return value;
    });

    Thread thread1 = new Thread(() -> {
      latch1.countDown();
      await(latch2);
      value1.set(lazy.get());
    });

    Thread thread2 = new Thread(() -> {
      await(latch1);
      latch2.countDown();
      value2.set(lazy.get());
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
