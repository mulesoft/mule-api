/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * {@link SmallMapDelegate} implementation for maps that have overflown into a {@link Map}
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class NSmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  private final Map<K, V> delegate;

  /**
   * Creates a new instance
   *
   * @param delegate      the delegate map
   * @param previousValue the last operation's previousValue (or null if non applicable)
   */
  public NSmallMapDelegate(Map<K, V> delegate, V previousValue) {
    this.delegate = delegate;
    this.previousValue = previousValue;
  }

  @Override
  SmallMapDelegate<K, V> fastPut(K key, V value) {
    previousValue = delegate.put(key, value);
    return this;
  }

  @Override
  SmallMapDelegate<K, V> fastRemove(Object key) {
    previousValue = delegate.remove(key);
    return this;
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return delegate.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return delegate.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return delegate.get(key);
  }

  @Override
  public Set<K> keySet() {
    return delegate.keySet();
  }

  @Override
  public Collection<V> values() {
    return delegate.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return delegate.entrySet();
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new NSmallMapDelegate<>(new HashMap<>(delegate), null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NSmallMapDelegate<?, ?> that = (NSmallMapDelegate<?, ?>) o;
    return Objects.equals(delegate, that.delegate);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }
}
