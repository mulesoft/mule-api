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

public class FastMap<K, V> implements Map<K, V>, Serializable {

  private FastMapDelegate<K, V> delegate;

  public static <K, V> FastMap<K, V> of(K key, V value) {
    return new FastMap<>(new UniFastMapDelegate<>(new FastMapEntry<>(key, value), null));
  }

  public static <K, V> FastMap<K, V> of(K k1, V v1, K k2, V v2) {
    return new FastMap<>(new BiFastMapDelegate<>(new FastMapEntry<>(k1, v1),
                                                 new FastMapEntry<>(k2, v2),
                                                 null));
  }

  public static <K, V> FastMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return new FastMap<>(new TriFastMapDelegate<>(new FastMapEntry<>(k1, v1),
                                                  new FastMapEntry<>(k2, v2),
                                                  new FastMapEntry<>(k3, v3),
                                                  null));
  }

  public static <K, V> FastMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return new FastMap<>(new QuadFastMapDelegate<>(new FastMapEntry<>(k1, v1),
                                                   new FastMapEntry<>(k2, v2),
                                                   new FastMapEntry<>(k3, v3),
                                                   new FastMapEntry<>(k4, v4),
                                                   null));
  }

  public static <K, V> FastMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return new FastMap<>(new PentaFastMapDelegate<>(new FastMapEntry<>(k1, v1),
                                                    new FastMapEntry<>(k2, v2),
                                                    new FastMapEntry<>(k3, v3),
                                                    new FastMapEntry<>(k4, v4),
                                                    new FastMapEntry<>(k5, v5),
                                                    null));
  }

  public static <K, V> FastMap<K, V> of(Map<K, V> map) {
    if (map == null) {
      return new FastMap<>(new EmptyFastMapDelegate<>(null));
    }

    if (map instanceof FastMap) {
      return ((FastMap<K, V>) map).copy();
    }

    Iterator<Entry<K, V>> it;
    switch (map.size()) {
      case 0:
        return new FastMap<>(new EmptyFastMapDelegate<>(null));
      case 1:
        return new FastMap<>(new UniFastMapDelegate<>(map.entrySet().iterator().next(), null));
      case 2:
        it = map.entrySet().iterator();
        return new FastMap<>(new BiFastMapDelegate<>(it.next(), it.next(), null));
      case 3:
        it = map.entrySet().iterator();
        return new FastMap<>(new TriFastMapDelegate<>(it.next(), it.next(), it.next(), null));
      case 4:
        it = map.entrySet().iterator();
        return new FastMap<>(new QuadFastMapDelegate<>(it.next(), it.next(), it.next(), it.next(), null));
      case 5:
        it = map.entrySet().iterator();
        return new FastMap<>(new PentaFastMapDelegate<>(it.next(), it.next(), it.next(), it.next(), it.next(), null));
      default:
        return new FastMap<>(new NFastMapDelegate<>(map, null));
    }
  }

  public FastMap() {
    delegate = new EmptyFastMapDelegate<>(null);
  }

  private FastMap(FastMapDelegate<K, V> delegate) {
    this.delegate = delegate;
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
    delegate = new EmptyFastMapDelegate<>(null);
  }

  public FastMap<K, V> copy() {
    return new FastMap<>(delegate.copy());
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
