/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import static org.mule.runtime.internal.util.collection.UnmodifiableMap.unmodifiableMap;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A {@link Map} implementation optimized for cases in which:
 * <ul>
 * <li>The size will not surpass the number of five entries, or will only surpass it in the minority of cases</li>
 * <li>The key's {@code equals(Object)} method is significantly faster than its {@code hashCode()} method</li>
 * </ul>
 * <p>
 * The way this map works is that instead of using a hash table, it will do a brute force search over its five keys. When the
 * above conditions are met, such approach is faster and consumes significantly less memory than a traditional {@link HashMap}.
 * <p>
 * Notice that the entire concept of this map is predicated on those two conditions. Using it in other situations will have the
 * opposite effect of actually decreasing performance. Ideal case if that of {@link String} keys. Because most keys will be of
 * different sizes, the equals method will be faster than the hashCode and the gain can be obtained. Notice that if we were to use
 * {@link Integer} instead, then the optimization would not occur as the hashCode of an int is the int itself.
 * <p>
 * If a seventh element is introduced into the map, then it will internally switch gears and start delegating into a
 * {@link HashMap}. Notice there's a performance penalty in copying the contents of this map into the new delegate.
 * <p>
 * Best performance can be obtained if:
 *
 * <ul>
 * <li>The {@code of()} factory methods are used to initialize instances (when possible)</li>
 * <li>The {@link #copy(Map)} and {@link #copy()} methods are used as the preferred way of copying maps</li>
 * <li>The {@link #unmodifiable(Map)} method is preferred over {@link Collections#unmodifiableMap(Map)}
 * <li>The {@link #forSize(int)} is the preferred way of initializing a map of a known size</li> or Guava's
 * {@code UnmodifiableMap}</li>
 * </ul>
 * <p>
 * NOTE: {@link #copy(Map)} should be used even if the input Map is not a SmallMap.
 * <p>
 * This map supports concurrent reads but doesn't support concurrent writes.
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
public class SmallMap<K, V> implements Map<K, V>, Serializable {

  private static final long serialVersionUID = 702299469995340780L;

  private SmallMapDelegate<K, V> delegate;

  /**
   * Creates a new instance of a single entry described by the given {@code key} and {@code value}
   *
   * @param key   the key
   * @param value the value
   * @param <K>   the generic type of the keys
   * @param <V>   the generic type of the values
   * @return a new instance
   */
  public static <K, V> SmallMap<K, V> of(K key, V value) {
    return new SmallMap<>(new UniSmallMapDelegate<>(new SmallMapEntry<>(key, value), null));
  }

  /**
   * Creates a new instance of a two entries described by the given keys and values
   *
   * @param k1  the first key
   * @param v1  the first value
   * @param k2  the second key
   * @param v2  the second value
   * @param <K> the generic type of the keys
   * @param <V> the generic type of the values
   * @return a new instance
   */
  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2) {
    return new SmallMap<>(new BiSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                   new SmallMapEntry<>(k2, v2),
                                                   null));
  }

  /**
   * Creates a new instance of a three entries described by the given keys and values
   *
   * @param k1  the first key
   * @param v1  the first value
   * @param k2  the second key
   * @param v2  the second value
   * @param k3  the third key
   * @param v3  the third value
   * @param <K> the generic type of the keys
   * @param <V> the generic type of the values
   * @return a new instance
   */
  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
    return new SmallMap<>(new TriSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                    new SmallMapEntry<>(k2, v2),
                                                    new SmallMapEntry<>(k3, v3),
                                                    null));
  }

  /**
   * Creates a new instance of a four entries described by the given keys and values
   *
   * @param k1  the first key
   * @param v1  the first value
   * @param k2  the second key
   * @param v2  the second value
   * @param k3  the third key
   * @param v3  the third value
   * @param k4  the fourth key
   * @param v4  the fourth value
   * @param <K> the generic type of the keys
   * @param <V> the generic type of the values
   * @return a new instance
   */
  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    return new SmallMap<>(new QuadSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                     new SmallMapEntry<>(k2, v2),
                                                     new SmallMapEntry<>(k3, v3),
                                                     new SmallMapEntry<>(k4, v4),
                                                     null));
  }

  /**
   * Creates a new instance of a five entries described by the given keys and values
   *
   * @param k1  the first key
   * @param v1  the first value
   * @param k2  the second key
   * @param v2  the second value
   * @param k3  the third key
   * @param v3  the third value
   * @param k4  the fourth key
   * @param v4  the fourth value
   * @param k5  the fifth key
   * @param v5  the fifth value
   * @param <K> the generic type of the keys
   * @param <V> the generic type of the values
   * @return a new instance
   */
  public static <K, V> SmallMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    return new SmallMap<>(new PentaSmallMapDelegate<>(new SmallMapEntry<>(k1, v1),
                                                      new SmallMapEntry<>(k2, v2),
                                                      new SmallMapEntry<>(k3, v3),
                                                      new SmallMapEntry<>(k4, v4),
                                                      new SmallMapEntry<>(k5, v5),
                                                      null));
  }

  /**
   * Returns a copy of the given {@code map}. If the size of the map is lower or equal to five, then the result will be a new
   * {@link SmallMap}. Otherwise, a new {@link HashMap} will be created with a matching initial capacity.
   * <p>
   * This method offers better performance when the {@code map} is an instance of this class, as custom code is used to more
   * efficiently copy the underlying data structures used by this class. Therefore, the {@link #unmodifiable(Map)} method should
   * be preferred over other similar alternatives in order to leverage said optimization.
   *
   * @param map the map to be copied
   * @param <K> the generic type of the key
   * @param <V> the generic type of the value
   * @return a copy of the {@code map}
   */
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

  /**
   * Creates a new map optimized to have the given {@code size}. If {@code size} is lower or equal than 5, then a SmallMap will be
   * returned. Otherwise, a {@link HashMap} with {@code size} as the initial capacity will be produced.
   *
   * @param size the map's size
   * @param <K>  the generic type of the key
   * @param <V>  the generic type of the value
   * @return a new Map
   */
  public static <K, V> Map<K, V> forSize(int size) {
    return size < 5 ? new SmallMap<>() : new HashMap<>(size);
  }

  /**
   * Returns an unmodifiable version of the given {@code map}.
   *
   * @param map
   * @param <K>
   * @param <V>
   * @return
   */
  public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
    if (map instanceof UnmodifiableSmallMap) {
      return map;
    } else if (map instanceof SmallMap) {
      return new UnmodifiableSmallMap<>((SmallMap) map);
    } else {
      return unmodifiableMap(map);
    }
  }

  /**
   * Creates a new empty instance
   */
  public SmallMap() {
    delegate = new EmptySmallMapDelegate<>(null);
  }

  /**
   * Creates a new instance backed by the given {@code delegate}
   * 
   * @param delegate a delegate
   */
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

  /**
   * Creates a copy of this map. This is the most performing way of copying a SmallMap
   * 
   * @return a copy of {@code this} instance.
   */
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
  public String toString() {
    return delegate.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SmallMap) {
      return delegate.equals(((SmallMap) o).delegate);
    } else if (o instanceof Map) {
      return Objects.equals(entrySet(), ((Map) o).entrySet());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
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
