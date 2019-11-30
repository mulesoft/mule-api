/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.unmodifiableMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public class SmallMap<K, V> implements Map<K, V>, Serializable {

  protected SmallMapDelegate<K, V> delegate;

  public static <K, V> SmallMap<K, V> of(K key, V value) {
    return new SmallMap<>(new UniSmallMapDelegate<>(new SmallMapEntry<>(key, value), null));
  }

  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2) {
    return new SmallMap<>(new BiSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                   new SmallMapEntry<>(k2, v2),
                                                   null));
  }

  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return new SmallMap<>(new TriSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                    new SmallMapEntry<>(k2, v2),
                                                    new SmallMapEntry<>(k3, v3),
                                                    null));
  }

  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return new SmallMap<>(new QuadSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                     new SmallMapEntry<>(k2, v2),
                                                     new SmallMapEntry<>(k3, v3),
                                                     new SmallMapEntry<>(k4, v4),
                                                     null));
  }

  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return new SmallMap<>(new PentaSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                      new SmallMapEntry<>(k2, v2),
                                                      new SmallMapEntry<>(k3, v3),
                                                      new SmallMapEntry<>(k4, v4),
                                                      new SmallMapEntry<>(k5, v5),
                                                      null));
  }

  public static <K, V> Map<K, V> copy(Map<K, V> map) {
    if (map == null) {
      return new SmallMap<>(new EmptySmallMapDelegate<>(null));
    }

    if (map instanceof SmallMap) {
      return ((SmallMap<K, V>) map).copy();
    }

    Iterator<Entry<K, V>> it;
    switch (map.size()) {
      case 0:
        return new SmallMap<>(new EmptySmallMapDelegate<>(null));
      case 1:
        return new SmallMap<>(new UniSmallMapDelegate<>(map.entrySet().iterator().next(), null));
      case 2:
        it = map.entrySet().iterator();
        return new SmallMap<>(new BiSmallMapDelegate<>(it.next(), it.next(), null));
      case 3:
        it = map.entrySet().iterator();
        return new SmallMap<>(new TriSmallMapDelegate<>(it.next(), it.next(), it.next(), null));
      case 4:
        it = map.entrySet().iterator();
        return new SmallMap<>(new QuadSmallMapDelegate<>(it.next(), it.next(), it.next(), it.next(), null));
      case 5:
        it = map.entrySet().iterator();
        return new SmallMap<>(new PentaSmallMapDelegate<>(it.next(), it.next(), it.next(), it.next(), it.next(), null));
      default:
        return new HashMap<>(map);
    }
  }

  public static <K, V> Map<K, V> forSize(int size) {
    return size < 6 ? new SmallMap<>() : new HashMap<>(size);
  }

  public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
    if (map instanceof UnmodifiableSmallMap) {
      return map;
    } else if (map instanceof SmallMap) {
      return new UnmodifiableSmallMap<>((SmallMap) map);
    } else {
      return unmodifiableMap(map);
    }
  }

  public SmallMap() {
    delegate = new EmptySmallMapDelegate<>(null);
  }

  private SmallMap(SmallMapDelegate<K, V> delegate) {
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

    for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
      delegate = delegate.fastPut(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public V remove(Object key) {
    delegate = delegate.fastRemove(key);
    return delegate.getPreviousValue();
  }

  @Override
  public void clear() {
    delegate = new EmptySmallMapDelegate<>(null);
  }

  public SmallMap<K, V> copy() {
    return new SmallMap<>(delegate.copy());
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

  private static class UnmodifiableSmallMap<K, V> extends SmallMap<K, V> {

    public UnmodifiableSmallMap(SmallMap<K, V> delegate) {
      super(delegate.delegate);
    }

    @Override
    public V put(K key, V value) {
      throw unsupported();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
      throw unsupported();
    }

    @Override
    public void clear() {
      throw unsupported();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      throw unsupported();
    }

    @Override
    public V remove(Object key) {
      throw unsupported();
    }

    @Override
    public boolean remove(Object key, Object value) {
      throw unsupported();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
      throw unsupported();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      throw unsupported();
    }

    private UnsupportedOperationException unsupported() {
      throw new UnsupportedOperationException("Map is unmodifiable");
    }
  }
}
