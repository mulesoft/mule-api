/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.mule.runtime.api.store.ObjectStore;
import org.mule.runtime.api.store.ObjectStoreToMapAdapter;
import org.mule.runtime.api.store.SimpleMemoryObjectStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import io.qameta.allure.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectStoreToMapAdapterTestCase {

  private Map<String, Serializable> map;

  @Before
  public void setup() {
    map = new SimpleMemoryObjectStoreAsMap<>();
  }

  private static class SimpleMemoryObjectStoreAsMap<T extends Serializable> extends ObjectStoreToMapAdapter<T> {

    private final SimpleMemoryObjectStore<T> objectStore = new SimpleMemoryObjectStore<>();

    @Override
    public ObjectStore<T> getObjectStore() {
      return objectStore;
    }
  }

  @Test
  public void isEmptyForNewMap() {
    assertThat(map.isEmpty(), is(true));
  }

  @Test
  public void addMakesIsEmptyFalse() {
    map.put("Hello", 5);
    assertThat(map.isEmpty(), is(false));
  }

  @Test
  public void sizeForNewMap() {
    assertThat(map.size(), is(0));
  }

  @Test
  public void sizeIncrementsWhenAddingElements() {
    map.put("Hello", 5);
    assertThat(map.size(), is(1));

    map.put("Goodbye", 5);
    assertThat(map.size(), is(2));
  }

  @Test
  public void getReturnsCorrectValue() {
    map.put("Hello", 5);
    map.put("Goodbye", 6);
    assertThat(map.get("Hello"), is(5));
    assertThat(map.get("Goodbye"), is(6));
  }

  @Test
  public void returnNullIfKeyDoesNotExist() {
    assertThat(map.get("Hello"), nullValue());
  }

  @Test
  public void replacesValueWithSameKey() {
    map.put("Hello", "old");
    map.put("Hello", "new");

    assertThat(map.get("Hello"), is("new"));
    assertThat(map.size(), is(1));
  }

  @Test
  public void doesNotOverwriteSeperateKeysWithSameHash() {
    map.put("Ea", 5);
    map.put("FB", 6);

    assertThat(map.get("Ea"), is(5));
    assertThat(map.get("FB"), is(6));
  }

  @Test
  public void removeUnexistentKeyDoesNotThrowExceptionAndReturnsNull() {
    assertThat(map.remove("Hello"), nullValue());
  }

  @Test
  public void removeDecrementsSize() {
    map.put("Hello", 5);
    map.put("Goodbye", 6);
    assertThat(map.size(), is(2));

    map.remove("Hello");
    assertThat(map.size(), is(1));

    map.remove("Goodbye");
    assertThat(map.size(), is(0));
  }

  @Test
  public void removeDeletesElement() {
    map.put("Hello", 5);
    map.remove("Hello");

    assertThat(map.get("Hello"), nullValue());
  }

  @Test
  public void containsKeyForNewMap() {
    assertThat(map.containsKey("Hello"), is(false));
  }

  @Test
  public void containsKeyForNonExistingKey() {
    map.put("Hello", 5);
    assertThat(map.containsKey("Goodbye"), is(false));
  }

  @Test
  public void containsKeyForExistingKey() {
    map.put("Hello", 5);
    assertThat(map.containsKey("Hello"), is(true));
  }

  @Test
  public void containsKeyForKeyWithEquivalentHash() {
    map.put("Ea", 5);
    assertThat(map.containsKey("FB"), is(false));
  }

  @Test
  @Issue("MULE-18172")
  public void getDoesntThrowExceptionsWhenKeyIsUpdatedConcurrently() throws InterruptedException {
    int putThreadsCount = 100;
    int getThreadsCount = 100;
    int threadsSum = putThreadsCount + getThreadsCount;
    CountDownLatch threadsStartedLatch = new CountDownLatch(threadsSum);
    Semaphore startProcessingSemaphore = new Semaphore(0);

    AtomicInteger numberOfExceptions = new AtomicInteger(0);
    List<Thread> runningThreads = new ArrayList<>();

    // Spawn put threads.
    for (int i = 0; i < putThreadsCount; ++i) {
      Thread putThread = new Thread(() -> {
        try {
          threadsStartedLatch.countDown();
          startProcessingSemaphore.acquire();

          map.put("Hello", 5);
        } catch (Exception e) {
          numberOfExceptions.incrementAndGet();
        }
      });
      putThread.start();
      runningThreads.add(putThread);
    }

    // Spawn getters.
    for (int i = 0; i < getThreadsCount; ++i) {
      Thread putThread = new Thread(() -> {
        try {
          threadsStartedLatch.countDown();
          startProcessingSemaphore.acquire();

          map.get("Hello");
        } catch (Exception e) {
          numberOfExceptions.incrementAndGet();
        }
      });
      putThread.start();
      runningThreads.add(putThread);
    }

    // Wait for all threads to be ready to process.
    threadsStartedLatch.await();

    // Signal the threads to start processing.
    startProcessingSemaphore.release(threadsSum);

    // Wait for threads completion.
    for (Thread thread : runningThreads) {
      thread.join();
    }

    assertThat(numberOfExceptions.get(), is(0));
  }
}
