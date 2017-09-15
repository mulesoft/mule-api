/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a multi-map that allows the aggregation of keys and access to the aggregated list or a single value (the
 * first).
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @since 1.0
 */
public class MultiMap<K, V> implements Map<K, V>, Serializable {

  private static final long serialVersionUID = -4723226524524565104L;

  protected Map<K, LinkedList<V>> paramsMap;

  public MultiMap(final MultiMap<K, V> multiMap) {
    this.paramsMap = new LinkedHashMap<>(multiMap.paramsMap);
  }

  public MultiMap(final Map<K, V> parametersMap) {
    this.paramsMap = parametersMap.entrySet().stream().collect(toMap(Entry::getKey, e -> {
      LinkedList<V> values = new LinkedList<>();
      if (e.getValue() instanceof Collection) {
        values.addAll((Collection<? extends V>) e.getValue());
      } else {
        values.add(e.getValue());
      }
      return values;
    }));
    this.paramsMap = unmodifiableMap(paramsMap);
  }

  public MultiMap() {
    this.paramsMap = new LinkedHashMap<>();
  }

  public MultiMap<K, V> toImmutableMultiMap() {
    return new MultiMap(this.paramsMap);
  }

  @Override
  public int size() {
    return paramsMap.values().stream().reduce(0, (count, list) -> list.size() + count, (count, list) -> count);
  }

  @Override
  public boolean isEmpty() {
    return paramsMap.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return paramsMap.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return paramsMap.values().stream().anyMatch(list -> list.contains(value));
  }

  @Override
  public V get(Object key) {
    final LinkedList<V> values = paramsMap.get(key);
    if (values != null) {
      return values.getFirst();
    }
    return null;
  }

  /**
   * Returns the values to which the specified key is mapped.
   *
   * @param key the key whose associated values are to be returned
   * @return a list of values to which the specified key is mapped, or
   *         an empty list if this map contains no mappings for the key
   */
  public List<V> getAll(Object key) {
    return paramsMap.containsKey(key) ? unmodifiableList(paramsMap.get(key)) : emptyList();
  }

  @Override
  public V put(K key, V value) {
    LinkedList<V> previousValue = paramsMap.get(key);
    LinkedList<V> newValue = previousValue;
    if (previousValue != null) {
      previousValue = new LinkedList<>(previousValue);
    } else {
      newValue = new LinkedList<>();
    }
    newValue.add(value);
    paramsMap.put(key, newValue);
    if (previousValue == null || previousValue.isEmpty()) {
      return null;
    }
    return previousValue.getFirst();
  }

  /**
   * Associates the specified value with the specified key in this map
   * (optional operation).  If the map previously contained mappings for
   * the key, the new values are aggregated to the old.
   *
   * @param key key with which the specified values are to be associated
   * @param values collection of values to be associated with the specified key
   */
  public void put(K key, Collection<V> values) {
    LinkedList<V> newValue = paramsMap.get(key);
    if (newValue == null) {
      newValue = new LinkedList<>();
    }
    newValue.addAll(values);
    paramsMap.put(key, newValue);
  }

  @Override
  public V remove(Object key) {
    Collection<V> values = paramsMap.remove(key);
    if (values != null) {
      return values.iterator().next();
    }
    return null;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> aMap) {
    aMap.forEach((key, value) -> {
      LinkedList<V> values = new LinkedList<>();
      values.add(value);
      paramsMap.put(key, values);
    });
  }

  @Override
  public void clear() {
    paramsMap.clear();
  }

  @Override
  public Set<K> keySet() {
    return paramsMap.keySet();
  }

  @Override
  public Collection<V> values() {
    List<V> values = new ArrayList<>();
    paramsMap.forEach((key, value) -> values.add(value.getFirst()));
    return values;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    paramsMap.forEach((key, value) -> entries.add(new AbstractMap.SimpleEntry<>(key, value.getFirst())));
    return entries;
  }

  /**
   * Returns a {@link List} view of the mappings contained in this map, including an entry for each value associated to the same
   * key.
   *
   * @return a list view of the mappings contained in this map
   */
  public List<Entry<K, V>> entryList() {
    List<Entry<K, V>> entries = new LinkedList<>();
    paramsMap.forEach((key, values) -> values.forEach(value -> entries.add(new AbstractMap.SimpleEntry<>(key, value))));
    return entries;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof MultiMap && paramsMap.equals(((MultiMap) o).paramsMap);
  }

  @Override
  public int hashCode() {
    return paramsMap.hashCode();
  }

  public Map<K, ? extends List<V>> toListValuesMap() {
    return unmodifiableMap(paramsMap);
  }

  @Override
  public String toString() {
    return "MultiMap{" + Arrays.toString(paramsMap.entrySet().toArray()) + '}';
  }
}
