/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import org.mule.runtime.api.exception.MuleRuntimeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class FastMap<K, V> implements Map<K, V>, Serializable {

  private FastMapDelegate<K, V> delegate;
  private transient final Supplier<Map<K, V>> overflowDelegateFactory;

  public static <K, V> FastMap<K, V> of(Map<K, V> map) {
    if (map == null) {
      return new FastMap<>(HashMap::new, new EmptyFastMapDelegate<>(HashMap::new, null));
    }

    if (map instanceof FastMap) {
      return ((FastMap<K, V>) map).copy();
    }

    Supplier<Map<K, V>> mapSupplier = () -> {
      try {
        return map.getClass().newInstance();
      } catch (Exception e) {
        throw new MuleRuntimeException(e);
      }
    };
    Iterator<Entry<K, V>> it;
    switch (map.size()) {
      case 0:
        return new FastMap<>(mapSupplier, new EmptyFastMapDelegate<>(mapSupplier, null));
      case 1:
        return new FastMap<>(mapSupplier, new UniFastMapDelegate<>(mapSupplier, map.entrySet().iterator().next(), null));
      case 2:
        it = map.entrySet().iterator();
        return new FastMap<>(mapSupplier, new BiFastMapDelegate<>(mapSupplier, it.next(), it.next(), null));
      case 3:
        it = map.entrySet().iterator();
        return new FastMap<>(mapSupplier, new TriFastMapDelegate<>(mapSupplier, it.next(), it.next(), it.next(), null));
      case 4:
        it = map.entrySet().iterator();
        return new FastMap<>(mapSupplier,
                             new QuadFastMapDelegate<>(mapSupplier, it.next(), it.next(), it.next(), it.next(), null));
      case 5:
        it = map.entrySet().iterator();
        return new FastMap<>(mapSupplier,
                             new PentaFastMapDelegate<>(mapSupplier, it.next(), it.next(), it.next(), it.next(), it.next(),
                                                        null));
      default:
        return new FastMap<>(mapSupplier, new NFastMapDelegate<>(mapSupplier, map, null));
    }
  }

  public FastMap() {
    this(HashMap::new);
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

  public FastMap<K, V> copy() {
    return new FastMap<>(overflowDelegateFactory, delegate.copy());
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
    while (i.hasNext()) {
      h += i.next().hashCode();
    }
    return h;
  }
}
