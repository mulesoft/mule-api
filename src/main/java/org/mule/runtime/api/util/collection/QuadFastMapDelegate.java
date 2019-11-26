/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class QuadFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private final Entry<K, V> entry1;
  private final Entry<K, V> entry2;
  private final Entry<K, V> entry3;
  private final Entry<K, V> entry4;

  public QuadFastMapDelegate(Entry<K, V> entry1,
                             Entry<K, V> entry2,
                             Entry<K, V> entry3,
                             Entry<K, V> entry4,
                             V previousValue) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.entry3 = entry3;
    this.entry4 = entry4;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 4;
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
        || Objects.equals(entry4.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value)
        || Objects.equals(entry2.getKey(), value)
        || Objects.equals(entry3.getKey(), value)
        || Objects.equals(entry4.getKey(), value);
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

    return unmodifiableSet(keys);
  }

  @Override
  public Collection<V> values() {
    Set<V> values = new HashSet<>();
    values.add(entry1.getValue());
    values.add(entry2.getValue());
    values.add(entry3.getValue());
    values.add(entry4.getValue());

    return unmodifiableSet(values);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    entries.add(entry1);
    entries.add(entry2);
    entries.add(entry3);
    entries.add(entry4);

    return unmodifiableSet(entries);
  }

  @Override
  public FastMapDelegate<K, V> fastPut(K key, V value) {
    if (Objects.equals(entry1.getKey(), key)) {
      previousValue = entry1.getValue();
      entry1.setValue(value);
      return this;
    } else if (Objects.equals(entry2.getKey(), key)) {
      previousValue = entry2.getValue();
      entry2.setValue(value);
      return this;
    } else if (Objects.equals(entry3.getKey(), key)) {
      previousValue = entry3.getValue();
      entry3.setValue(value);
      return this;
    } else if (Objects.equals(entry4.getKey(), key)) {
      previousValue = entry4.getValue();
      entry4.setValue(value);
      return this;
    } else {
      previousValue = null;
      return new PentaFastMapDelegate(entry1, entry2, entry3, entry4, new FastMapEntry<>(key, value), null);
    }
  }

  @Override
  public FastMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new TriFastMapDelegate<>(entry2, entry3, entry4, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new TriFastMapDelegate<>(entry1, entry3, entry4, entry2.getValue());
    } else if (Objects.equals(entry3.getKey(), key)) {
      return new TriFastMapDelegate<>(entry1, entry2, entry4, entry3.getValue());
    } else if (Objects.equals(entry4.getKey(), key)) {
      return new TriFastMapDelegate<>(entry1, entry2, entry3, entry4.getValue());
    } else {
      previousValue = null;
      return this;
    }
  }

  @Override
  FastMapDelegate<K, V> copy() {
    return new QuadFastMapDelegate<>(entry1, entry2, entry3, entry4, null);
  }
}
