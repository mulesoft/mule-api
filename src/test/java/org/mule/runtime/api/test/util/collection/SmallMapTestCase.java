/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.util.collection;

import static java.lang.String.valueOf;
import static java.util.Collections.singletonMap;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.api.util.collection.Collectors.toImmutableList;
import static org.mule.runtime.api.util.collection.SmallMap.unmodifiable;

import org.mule.runtime.api.util.collection.SmallMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import io.qameta.allure.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SmallMapTestCase {

  private static final String[] KEYS = new String[] {"one", "two", "three", "four", "five", "six", "seven"};
  private static final String[] VALUES = new String[] {"uno", "dos", "tres", "cuatro", "cinco", "seis", "siete"};

  @Parameters(name = "with Size: {0}")
  public static Iterable<Integer> data() {
    return range(0, 8).boxed().collect(toImmutableList());
  }

  private final int mapSize;
  private Map<String, String> map = new SmallMap<>();

  public SmallMapTestCase(int mapSize) {
    this.mapSize = mapSize;
  }

  @Before
  public void before() {
    assertThat(map.size(), is(0));
    assertThat(map.isEmpty(), is(true));

    populate(mapSize);
  }

  private void populate(int size) {
    populate(size, map);
  }

  private void populate(int size, Map<String, String> map) {
    map.clear();

    for (int i = 0; i < size; i++) {
      map.put(KEYS[i], VALUES[i]);
      assertThat(map.size(), is(i + 1));
      assertThat(map.containsKey(KEYS[i]), is(true));
      assertThat(map.isEmpty(), is(false));
    }
  }

  @Test
  public void putRepeatedKeys() {
    for (int i = 0; i < mapSize; i++) {
      assertThat(map.put(KEYS[i], VALUES[i].toUpperCase()), is(VALUES[i]));
      assertThat(map.get(KEYS[i]), equalTo(VALUES[i].toUpperCase()));
      assertThat(map.size(), is(mapSize));
      assertThat(map.containsKey(KEYS[i]), is(true));
      assertThat(map.isEmpty(), is(false));
    }
  }

  @Test
  public void isEmpty() {
    assertThat(map.isEmpty(), is(mapSize == 0));
  }

  @Test
  public void removeKeysInRandomOrder() {
    Random random = new Random();
    Set<Integer> removedKeyIndex;

    for (int i = 0; i < 10; i++) {
      populate(mapSize);
      removedKeyIndex = new HashSet<>();

      while (!map.isEmpty()) {
        int index;
        do {
          index = random.nextInt(mapSize);
        } while (!removedKeyIndex.add(index));

        assertThat(map.remove(KEYS[index]), is(VALUES[index]));
        assertThat(map.isEmpty(), is(removedKeyIndex.size() == mapSize));
        assertThat(map.size(), is(mapSize - removedKeyIndex.size()));
      }
    }

    assertThat(map.isEmpty(), is(true));
    assertThat(map.size(), is(0));
  }

  @Test
  public void putAll() {
    map.clear();

    String originalKey = "original";
    String originalValue = "value";

    map.put(originalKey, originalValue);
    Map<String, String> other = new HashMap<>();

    int otherMapSize = 5;
    for (int i = 0; i < otherMapSize; i++) {
      other.put(valueOf(i), "_" + i);
    }

    other.put(originalKey, originalValue);

    map.putAll(other);
    assertThat(map.size(), is(otherMapSize + 1));
    assertThat(map.isEmpty(), is(false));

    for (int i = 0; i < otherMapSize; i++) {
      final String key = valueOf(i);
      final String value = "_" + i;

      assertThat(map.containsKey(key), is(true));
      assertThat(map.containsValue(value), is(true));
      assertThat(map.get(key), equalTo(value));
    }

    assertThat(map.containsKey(originalKey), is(true));
    assertThat(map.containsValue(originalValue), is(true));
    assertThat(map.get(originalKey), equalTo(originalValue));
  }

  @Test
  public void clear() {
    map.clear();
    assertThat(map.isEmpty(), is(true));
    assertThat(map.size(), is(0));

    populate(mapSize);
  }

  @Test
  public void entrySet() {
    map.entrySet().forEach(entry -> {
      int i = getKeyIndex(entry.getKey());
      assertThat(entry.getValue(), equalTo(VALUES[i]));
    });
  }

  @Test
  public void keySet() {
    Collection<String> values = map.keySet();
    assertThat(values, hasSize(mapSize));
    for (int i = 0; i < mapSize; i++) {
      assertThat(values.contains(KEYS[i]), is(true));
    }
  }

  @Test
  public void contains() {
    for (int i = 0; i < mapSize; i++) {
      assertThat(map.containsValue(VALUES[i]), is(true));
    }

    assertThat(map.containsValue("bleh"), is(false));
  }

  @Test
  public void values() {
    Collection<String> values = map.values();
    assertThat(values, hasSize(mapSize));
    for (int i = 0; i < mapSize; i++) {
      assertThat(values.contains(VALUES[i]), is(true));
    }
  }

  @Test
  public void remove() {
    for (int i = 0; i < mapSize;) {
      assertThat(map.remove(KEYS[i]), is(VALUES[i]));
      assertThat(map.size(), is(mapSize - ++i));
    }
  }

  @Test
  public void mutateEntry() {
    map.entrySet().forEach(entry -> entry.setValue(entry.getValue().toUpperCase()));

    for (int i = 0; i < map.size(); i++) {
      assertThat(map.get(KEYS[i]), equalTo(VALUES[i].toUpperCase()));
    }
  }

  @Test
  public void size() {
    assertThat(map.size(), is(mapSize));
  }

  @Test
  public void mapToString() {
    Map<String, String> map = new SmallMap<>();
    map.put("a", "b");
    map.put("c", "d");

    assertThat(map.toString(), equalTo("{a=b, c=d}"));
  }

  @Test
  public void equals() {
    Map<String, String> other = new HashMap<>();
    populate(mapSize, other);

    assertThat(map, equalTo(other));
    other.remove(KEYS[0]);

    if (mapSize > 0) {
      assertThat(map, not(equalTo(other)));
    }
  }

  @Test
  public void setToString() {
    Map<String, String> map = new SmallMap<>();
    map.put("a", "b");
    map.put("c", "d");

    assertThat(map.toString(), equalTo("{a=b, c=d}"));
  }

  private int getKeyIndex(String key) {
    for (int i = 0; i < KEYS.length; i++) {
      if (KEYS[i].equals(key)) {
        return i;
      }
    }

    throw new NoSuchElementException();
  }

  @Test
  @Issue("MULE-19180")
  public void unmodifiableMapUsingSmallMap() {
    // Create unmodifiable map
    Map<String, String> unmodifiableMap = unmodifiable(map);
    assertThat(map, not(sameInstance(unmodifiableMap)));

    // An other unmodifiable from previous one
    Map<String, String> unmodifiable2 = unmodifiable(unmodifiableMap);
    assertThat(unmodifiableMap, is(sameInstance(unmodifiable2)));
  }

  @Test
  @Issue("MULE-19180")
  public void unmodifiableMapUsingMap() {
    // Create unmodifiable map
    Map<String, String> source = singletonMap("key", "value");
    Map<String, String> unmodifiableMap = unmodifiable(source);
    assertThat(source, not(sameInstance(unmodifiableMap)));

    // An other unmodifiable from previous one
    Map<String, String> unmodifiable2 = unmodifiable(unmodifiableMap);
    assertThat(unmodifiableMap, is(sameInstance(unmodifiable2)));
  }
}
