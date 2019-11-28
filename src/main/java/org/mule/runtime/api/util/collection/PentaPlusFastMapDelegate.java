/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class PentaPlusFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private Entry<K, V> entry1;
  private Entry<K, V> entry2;
  private Entry<K, V> entry3;
  private Entry<K, V> entry4;
  private Entry<K, V> entry5;
  private Map<K, V> overflow;

  public PentaPlusFastMapDelegate(Entry<K, V> entry1,
                                  Entry<K, V> entry2,
                                  Entry<K, V> entry3,
                                  Entry<K, V> entry4,
                                  Entry<K, V> entry5,
                                  V previousValue,
                                  Map<K, V> overflow) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.entry3 = entry3;
    this.entry4 = entry4;
    this.entry5 = entry5;
    this.previousValue = previousValue;
    this.overflow = overflow;
  }

  @Override
  public int size() {
    return 5 + (overflow != null ? overflow.size() : 0);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return Objects.equals(entry1.getKey(), key)
        || Objects.equals(entry2.getKey(), key)
        || Objects.equals(entry3.getKey(), key)
        || Objects.equals(entry4.getKey(), key)
        || Objects.equals(entry5.getKey(), key)
        || (overflow != null && overflow.containsKey(key));
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value)
        || Objects.equals(entry2.getValue(), value)
        || Objects.equals(entry3.getValue(), value)
        || Objects.equals(entry4.getValue(), value)
        || Objects.equals(entry5.getValue(), value)
        || (overflow != null && overflow.containsKey(value));
  }

  @Override
  public V get(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return entry1.getValue();
    } else if (Objects.equals(entry2.getKey(), key)) {
      return entry2.getValue();
    } else if (Objects.equals(entry3.getKey(), key)) {
      return entry3.getValue();
    } else if (Objects.equals(entry4.getKey(), key)) {
      return entry4.getValue();
    } else if (Objects.equals(entry5.getKey(), key)) {
      return entry5.getValue();
    } else {
      return overflow != null ? overflow.get(key) : null;
    }
  }

  @Override
  public Set<K> keySet() {
    int size = 5 + (overflow != null ? overflow.size() : 0);
    List<K> keys = new ArrayList<>(size);
    keys.add(entry1.getKey());
    keys.add(entry2.getKey());
    keys.add(entry3.getKey());
    keys.add(entry4.getKey());
    keys.add(entry5.getKey());

    if (overflow != null) {
      keys.addAll(overflow.keySet());
    }

    return new UnmodifiableSetAdapter<>(keys);
  }

  @Override
  public Collection<V> values() {
    int size = 5 + (overflow != null ? overflow.size() : 0);
    List<V> values = new ArrayList<>(size);
    values.add(entry1.getValue());
    values.add(entry2.getValue());
    values.add(entry3.getValue());
    values.add(entry4.getValue());
    values.add(entry5.getValue());

    if (overflow != null) {
      values.addAll(overflow.values());
    }

    return unmodifiableList(values);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    int size = 5 + (overflow != null ? overflow.size() : 0);
    List<Entry<K, V>> entries = new ArrayList<>(size);

    entries.add(entry1);
    entries.add(entry2);
    entries.add(entry3);
    entries.add(entry4);
    entries.add(entry5);

    if (overflow != null) {
      entries.addAll(overflow.entrySet());
    }

    return new UnmodifiableSetAdapter<>(entries);
  }

  @Override
  public FastMapDelegate<K, V> fastPut(K key, V value) {
    if (Objects.equals(entry1.getKey(), key)) {
      previousValue = entry1.getValue();
      entry1 = new FastMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry2.getKey(), key)) {
      previousValue = entry2.getValue();
      entry2 = new FastMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry3.getKey(), key)) {
      previousValue = entry3.getValue();
      entry3 = new FastMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry4.getKey(), key)) {
      previousValue = entry4.getValue();
      entry4 = new FastMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry5.getKey(), key)) {
      previousValue = entry5.getValue();
      entry5 = new FastMapEntry<>(key, value);
      return this;
    } else {
      if (overflow == null) {
        overflow = new HashMap<>();
      }
      overflow.put(key, value);
      previousValue = null;
      return this;
    }
  }

  @Override
  public FastMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new QuadFastMapDelegate<>(entry2, entry3, entry4, entry5, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new QuadFastMapDelegate<>(entry1, entry3, entry4, entry5, entry2.getValue());
    } else if (Objects.equals(entry3.getKey(), key)) {
      return new QuadFastMapDelegate<>(entry1, entry2, entry4, entry5, entry3.getValue());
    } else if (Objects.equals(entry4.getKey(), key)) {
      return new QuadFastMapDelegate<>(entry1, entry2, entry3, entry5, entry4.getValue());
    } else if (Objects.equals(entry5.getKey(), key)) {
      return new QuadFastMapDelegate<>(entry1, entry2, entry3, entry4, entry5.getValue());
    } else {
      previousValue = null;
      //TODO: Implement this right if theory holds
      return this;
    }
  }

  @Override
  FastMapDelegate<K, V> copy() {
    return new PentaPlusFastMapDelegate<>(entry1, entry2, entry3, entry4, entry5, null, null);
  }
}
