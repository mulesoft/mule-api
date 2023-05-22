/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.internal.util.collection;

import static org.mule.runtime.internal.util.collection.UnmodifiableMap.unmodifiableMap;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.rules.ExpectedException.none;

import org.mule.runtime.internal.util.collection.UnmodifiableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.qameta.allure.Issue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@Issue("MULE-19180")
public class UnmodifiableMapTestCase {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  private Map<String, String> map;
  private Map<String, String> unmodifiable;

  @Rule
  public ExpectedException expectedException = none();

  @Before
  public void setup() {
    map = singletonMap(KEY, VALUE);
    unmodifiable = unmodifiableMap(map);
  }

  @Test
  public void avoidMultiplesUnmodifiableMapInstances() {
    assertThat(unmodifiable, is(instanceOf(UnmodifiableMap.class)));

    Map<String, String> unmodifiable2 = unmodifiableMap(unmodifiable);
    assertThat(unmodifiable2, is(instanceOf(UnmodifiableMap.class)));

    assertThat(unmodifiable, sameInstance(unmodifiable2));
  }

  @Test
  public void testUnmodifiableMapSize() {
    assertThat(unmodifiable.size(), is(1));
  }

  @Test
  public void testUnmodifiableMapIsEmpty() {
    Map<String, String> unmodifiable = unmodifiableMap(emptyMap());
    assertThat(unmodifiable.isEmpty(), is(true));
    unmodifiable = unmodifiableMap(map);
    assertThat(unmodifiable.isEmpty(), is(false));
  }

  @Test
  public void testUnmodifiableMapContainsKey() {
    assertThat(unmodifiable.containsKey(KEY), is(true));
    assertThat(unmodifiable.containsKey("other-key"), is(false));
  }

  @Test
  public void testUnmodifiableContainsValue() {
    assertThat(unmodifiable.containsValue(VALUE), is(true));
    assertThat(unmodifiable.containsValue("other-value"), is(false));
  }

  @Test
  public void testUnmodifiableGet() {
    String value = unmodifiable.get(KEY);
    assertThat(value, not(nullValue()));
    assertThat(value, is(VALUE));
    value = unmodifiable.get("other-key");
    assertThat(value, is(nullValue()));
  }

  @Test
  public void testUnmodifiablePut() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.put("other-key", "other-value");
  }

  @Test
  public void testUnmodifiableRemove() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.remove(KEY);
  }

  @Test
  public void testUnmodifiablePutAll() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.putAll(singletonMap("other-key", "other-value"));
  }

  @Test
  public void testUnmodifiableClear() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.clear();
  }

  @Test
  public void testUnmodifiableKeySet() {
    Set<String> keys = unmodifiable.keySet();
    assertThat(keys.size(), is(1));
    assertThat(keys, contains(KEY));
  }

  @Test
  public void testUnmodifiableValues() {
    Collection<String> values = unmodifiable.values();
    assertThat(values.size(), is(1));
    assertThat(values, contains(VALUE));
  }

  @Test
  public void testUnmodifiableEntrySet() {
    Set<Map.Entry<String, String>> entries = unmodifiable.entrySet();
    assertThat(entries.size(), is(1));
    Map.Entry<String, String> entry = entries.iterator().next();
    assertThat(entry.getKey(), is(KEY));
    assertThat(entry.getValue(), is(VALUE));
  }

  @Test
  public void testUnmodifiableGetOrDefault() {
    String value = unmodifiable.getOrDefault(KEY, "defaultValue");
    assertThat(value, not(nullValue()));
    assertThat(value, is(VALUE));
    value = unmodifiable.getOrDefault("other-key", "defaultValue");
    assertThat(value, not(nullValue()));
    assertThat(value, is("defaultValue"));
  }

  @Test
  public void testUnmodifiableForEach() {
    Set<String> keys = new HashSet<>();
    List<String> values = new ArrayList<>();
    unmodifiable.forEach((k, v) -> {
      keys.add(k);
      values.add(v);
    });
    assertThat(keys.size(), is(1));
    assertThat(keys, contains(KEY));

    assertThat(values.size(), is(1));
    assertThat(values, contains(VALUE));
  }

  @Test
  public void testUnmodifiableReplaceAll() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.replaceAll((k, v) -> v + "-suffix");
  }

  @Test
  public void testUnmodifiablePutIfAbsent() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.putIfAbsent("other-key", "other-value");
  }

  @Test
  public void testUnmodifiableRemoveKeyValue() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.remove(KEY, VALUE);
  }

  @Test
  public void testUnmodifiableReplaceKeyValue() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.replace(KEY, VALUE, "other-value");
  }

  @Test
  public void testUnmodifiableReplace() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.replace(KEY, "other-value");
  }

  @Test
  public void testUnmodifiableComputeIfAbsent() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.computeIfAbsent("other-key", k -> k + "_value");
  }

  @Test
  public void testUnmodifiableComputeIfPresent() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.computeIfPresent(KEY, (k, v) -> k + "_" + v + "_value");
  }

  @Test
  public void testUnmodifiableCompute() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.compute(KEY, (k, v) -> k + "_" + v + "_value");
  }

  @Test
  public void testUnmodifiableMerge() {
    expectedException.expect(UnsupportedOperationException.class);
    unmodifiable.merge(KEY, "other-value", (old, newValue) -> old + "_" + newValue);
  }
}
