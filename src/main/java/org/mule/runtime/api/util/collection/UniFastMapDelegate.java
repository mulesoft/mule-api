/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
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

class UniFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private Entry<K, V> entry;

  public UniFastMapDelegate(Entry<K, V> entry, V previousValue) {
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
    return containsKey(key) ? entry.getValue() : null;
  }

  @Override
  FastMapDelegate<K, V> fastPut(K key, V value) {
    if (containsKey(key)) {
      previousValue = entry.getValue();
      entry = new FastMapEntry<>(key, value);
      return this;
    } else {
      return new BiFastMapDelegate<>(entry, new FastMapEntry<>(key, value), null);
    }
  }

  @Override
  FastMapDelegate<K, V> fastRemove(Object key) {
    if (containsKey(key)) {
      return new EmptyFastMapDelegate<>(entry.getValue());
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
  FastMapDelegate<K, V> copy() {
    return new UniFastMapDelegate<>(entry, null);
  }
}
