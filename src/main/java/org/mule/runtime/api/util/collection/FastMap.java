/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class FastMap<K, V> implements Map<K, V>, Serializable {

  private FastMapDelegate<K, V> delegate;
  private final Supplier<Map<K, V>> overflowDelegateFactory;

  public static <K, V> FastMap<K, V> of(Map<K, V> map) {
    if (map instanceof FastMap) {
      FastMap<K, V> other = (FastMap<K, V>) map;
      return new FastMap<>(other.overflowDelegateFactory, other.delegate);
    }

    return null;
  }

  public FastMap(Supplier<Map<K, V>> overflowDelegateFactory) {
    this.overflowDelegateFactory = overflowDelegateFactory;
    delegate = new EmptyFastMapDelegate<>(overflowDelegateFactory, null);
  }

  private FastMap(Supplier<Map<K, V>> overflowDelegateFactory, FastMapDelegate<K, V> delegate) {
    this.delegate = delegate;
    this.overflowDelegateFactory = overflowDelegateFactory;
  }

  @Override
  public V put(K key, V value) {
    delegate = delegate.fastPut(key, value);
    return delegate.getPreviousValue();
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    if (map == null) {
      throw new NullPointerException();
    }

    map.forEach((key, value) -> delegate = delegate.fastPut(key, value));
  }

  @Override
  public V remove(Object key) {
    delegate = delegate.fastRemove(key);
    return delegate.getPreviousValue();
  }

  @Override
  public void clear() {
    delegate = new EmptyFastMapDelegate<>(overflowDelegateFactory, null);
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
  public boolean equals(Object o) {
    if (o instanceof Map) {
      return Objects.equals(entrySet(), ((Map) o).entrySet());
    }

    return false;
  }

  @Override
  public int hashCode() {
    int h = 0;
    Iterator<Entry<K, V>> i = entrySet().iterator();
    while (i.hasNext())
      h += i.next().hashCode();
    return h;
  }
}
