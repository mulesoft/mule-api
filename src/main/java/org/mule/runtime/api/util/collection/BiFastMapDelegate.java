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
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

class BiFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private final Entry<K, V> entry1;
  private final Entry<K, V> entry2;

  public BiFastMapDelegate(Supplier<Map<K, V>> overflowDelegateFactory,
                           Entry<K, V> entry1,
                           Entry<K, V> entry2,
                           V previousValue) {
    super(overflowDelegateFactory);
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 2;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return Objects.equals(entry1.getKey(), key) || Objects.equals(entry2.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value) || Objects.equals(entry2.getKey(), value);
  }

  @Override
  public V get(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return entry1.getValue();
    } else if (Objects.equals(entry2.getKey(), key)) {
      return entry2.getValue();
    } else {
      return null;
    }
  }

  @Override
  public Set<K> keySet() {
    Set<K> keys = new HashSet<>();
    keys.add(entry1.getKey());
    keys.add(entry2.getKey());

    return unmodifiableSet(keys);
  }

  @Override
  public Collection<V> values() {
    Set<V> values = new HashSet<>();
    values.add(entry1.getValue());
    values.add(entry2.getValue());

    return unmodifiableSet(values);
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    entries.add(entry1);
    entries.add(entry2);

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
    } else {
      previousValue = null;
      return new TriFastMapDelegate<>(overflowDelegateFactory, entry1, entry2, new FastMapEntry<>(key, value), null);
    }
  }

  @Override
  public FastMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new UniFastMapDelegate<>(overflowDelegateFactory, entry2, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new UniFastMapDelegate<>(overflowDelegateFactory, entry1, entry2.getValue());
    } else {
      previousValue = null;
      return this;
    }
  }

  @Override
  FastMapDelegate<K, V> copy() {
    return new BiFastMapDelegate<>(overflowDelegateFactory, entry1, entry2, null);
  }
}
