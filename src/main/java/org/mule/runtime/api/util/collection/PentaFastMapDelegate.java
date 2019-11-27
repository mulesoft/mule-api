/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class PentaFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private Entry<K, V> entry1;
  private Entry<K, V> entry2;
  private Entry<K, V> entry3;
  private Entry<K, V> entry4;
  private Entry<K, V> entry5;

  public PentaFastMapDelegate(Entry<K, V> entry1,
                              Entry<K, V> entry2,
                              Entry<K, V> entry3,
                              Entry<K, V> entry4,
                              Entry<K, V> entry5,
                              V previousValue) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.entry3 = entry3;
    this.entry4 = entry4;
    this.entry5 = entry5;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 5;
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
        || Objects.equals(entry5.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value)
        || Objects.equals(entry2.getKey(), value)
        || Objects.equals(entry3.getKey(), value)
        || Objects.equals(entry4.getKey(), value)
        || Objects.equals(entry5.getKey(), value);
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
      return null;
    }
  }

  @Override
  public Set<K> keySet() {
    Set<K> keys = new HashSet<>();
    keys.add(entry1.getKey());
    keys.add(entry2.getKey());
    keys.add(entry3.getKey());
    keys.add(entry4.getKey());
    keys.add(entry5.getKey());

    return unmodifiableSet(keys);
  }

  @Override
  public Collection<V> values() {
    List<V> values = new ArrayList<>(5);
    values.add(entry1.getValue());
    values.add(entry2.getValue());
    values.add(entry3.getValue());
    values.add(entry4.getValue());
    values.add(entry5.getValue());

    return unmodifiableList(values);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    entries.add(entry1);
    entries.add(entry2);
    entries.add(entry3);
    entries.add(entry4);
    entries.add(entry5);

    return unmodifiableSet(entries);
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
      previousValue = null;
      HashMap<K, V> target = new HashMap<>();
      target.put(key, value);
      return toNaryDelegate(target, null);
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
      return this;
    }
  }

  private NFastMapDelegate<K, V> toNaryDelegate(Map<K, V> target, V previousValue) {
    target.put(entry1.getKey(), entry1.getValue());
    target.put(entry2.getKey(), entry2.getValue());
    target.put(entry3.getKey(), entry3.getValue());
    target.put(entry4.getKey(), entry4.getValue());
    target.put(entry5.getKey(), entry5.getValue());

    return new NFastMapDelegate<>(target, previousValue);
  }

  @Override
  FastMapDelegate<K, V> copy() {
    return new PentaFastMapDelegate<>(entry1, entry2, entry3, entry4, entry5, null);
  }
}
