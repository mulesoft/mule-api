/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.util.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.mule.runtime.internal.util.collection.ImmutableMapCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ImmutableMapCollectorTestCase {

  private final ImmutableMapCollector<Fruit, String, Fruit> collector =
      new ImmutableMapCollector<>(f -> f.getClass().getName(), f -> f);

  @Test
  public void collect() {
    final List<Fruit> fruits = Arrays.asList(new Apple(), new Banana(), new Kiwi());
    Map<String, Fruit> map = fruits.stream().collect(collector);

    assertThat(map.size(), is(3));
    fruits.forEach(fruit -> {
      Fruit value = map.get(fruit.getClass().getName());
      assertThat(value, sameInstance(fruit));
    });
  }

  @Test
  public void emptyMap() {
    Map<String, Fruit> map = new ArrayList<Fruit>().stream().collect(collector);
    assertThat(map.isEmpty(), is(true));
  }

  private static class Fruit {
  }

  private static class Apple extends Fruit {
  }

  private static class Banana extends Fruit {
  }

  private static class Kiwi extends Fruit {
  }

}
