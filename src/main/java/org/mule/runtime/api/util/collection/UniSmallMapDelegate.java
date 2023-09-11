/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * {@link SmallMapDelegate} implementation for maps that have only one entry.
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class UniSmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  private Entry<K, V> entry;

  public UniSmallMapDelegate(Entry<K, V> entry, V previousValue) {
    this.entry = entry;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return Objects.equals(entry.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry.getValue(), value);
  }

  @Override
  public V get(Object key) {
    return Objects.equals(entry.getKey(), key) ? entry.getValue() : null;
  }

  @Override
  SmallMapDelegate<K, V> fastPut(K key, V value) {
    if (containsKey(key)) {
      previousValue = entry.getValue();
      entry = new SmallMapEntry<>(key, value);
      return this;
    } else {
      return new BiSmallMapDelegate<>(entry, new SmallMapEntry<>(key, value), null);
    }
  }

  @Override
  SmallMapDelegate<K, V> fastRemove(Object key) {
    if (containsKey(key)) {
      return new EmptySmallMapDelegate<>(entry.getValue());
    }

    return this;
  }

  @Override
  public Set<K> keySet() {
    return singleton(entry.getKey());
  }

  @Override
  public Collection<V> values() {
    return singletonList(entry.getValue());
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return singleton(entry);
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new UniSmallMapDelegate<>(entry, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UniSmallMapDelegate<?, ?> that = (UniSmallMapDelegate<?, ?>) o;
    return Objects.equals(entry, that.entry);
  }

  @Override
  public int hashCode() {
    return entry.hashCode();
  }
}
