/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.singleton;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

class UniFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private final Entry<K, V> entry;

  public UniFastMapDelegate(Supplier<Map<K, V>> overflowDelegateFactory, Entry<K, V> entry, V previousValue) {
    super(overflowDelegateFactory);
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
      entry.setValue(value);
      return this;
    } else {
      previousValue = null;
      return new BiFastMapDelegate<>(overflowDelegateFactory, entry, new FastMapEntry<>(key, value), null);
    }
  }

  @Override
  FastMapDelegate<K, V> fastRemove(Object key) {
    if (containsKey(key)) {
      return new EmptyFastMapDelegate<>(overflowDelegateFactory, entry.getValue());
    }

    previousValue = null;
    return this;
  }

  @Override
  public Set<K> keySet() {
    return singleton(entry.getKey());
  }

  @Override
  public Collection<V> values() {
    return singleton(entry.getValue());
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return singleton(entry);
  }

  @Override
  FastMapDelegate<K, V> copy() {
    return new UniFastMapDelegate<>(overflowDelegateFactory, entry, null);
  }
}
