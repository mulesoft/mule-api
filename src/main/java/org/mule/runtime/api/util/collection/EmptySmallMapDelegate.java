/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.emptySet;

import java.util.Collection;
import java.util.Set;

/**
 * {@link SmallMapDelegate} implementation for maps that don't yet have any items.
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class EmptySmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  public EmptySmallMapDelegate(V previousValue) {
    this.previousValue = previousValue;
  }

  @Override
  public SmallMapDelegate<K, V> fastPut(K key, V value) {
    return new UniSmallMapDelegate<>(new SmallMapEntry<>(key, value), null);
  }

  @Override
  public SmallMapDelegate<K, V> fastRemove(Object key) {
    previousValue = null;
    return this;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    return false;
  }

  @Override
  public V get(Object key) {
    return null;
  }

  @Override
  public Set<K> keySet() {
    return emptySet();
  }

  @Override
  public Collection<V> values() {
    return emptySet();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return emptySet();
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new EmptySmallMapDelegate<>(null);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof EmptySmallMapDelegate;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "{}";
  }
}
