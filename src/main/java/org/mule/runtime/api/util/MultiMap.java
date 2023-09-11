/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.lang.Math.max;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.mule.runtime.api.metadata.DataType.MULTI_MAP_STRING_STRING;

import org.mule.runtime.api.el.DataTypeAware;
import org.mule.runtime.api.metadata.DataType;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Implementation of a multi-map that allows the aggregation of keys and access to the aggregated list or a single value (the
 * first).
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @since 1.0
 */
public class MultiMap<K, V> implements Map<K, V>, Serializable {

  private static final long serialVersionUID = -4723226524524565104L;

  @SuppressWarnings("rawtypes")
  private static final MultiMap EMPTY_MAP = new MultiMap<>().toImmutableMultiMap();

  /**
   * Returns an empty multi-map (immutable). This map is serializable.
   *
   * <p>
   * This example illustrates the type-safe way to obtain an empty map:
   *
   * <pre>
   *
   * MultiMap&lt;String, Stirng&gt; s = MultiMap.emptyMultiMap();
   * </pre>
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @return an empty multi-map
   * @since 1.1.1
   */
  @SuppressWarnings("unchecked")
  public static <K, V> MultiMap<K, V> emptyMultiMap() {
    return EMPTY_MAP;
  }

  /**
   * Returns an unmodifiable view of the specified multi-map. This method allows modules to provide users with "read-only" access
   * to internal multi-maps. Query operations on the returned multi-map "read through" to the specified multi-map, and attempts to
   * modify the returned multi-map, whether direct or via its collection views, result in an
   * <tt>UnsupportedOperationException</tt>.
   * <p>
   * The returned map will be serializable if the specified map is serializable.
   *
   * @param <K> the class of the map keys
   * @param <V> the class of the map values
   * @param m   the multi-map for which an unmodifiable view is to be returned.
   * @return an unmodifiable view of the specified multi-map.
   */
  public static <K, V> MultiMap<K, V> unmodifiableMultiMap(MultiMap<K, V> m) {
    requireNonNull(m);
    if (m instanceof UnmodifiableMultiMap || m instanceof ImmutableMultiMap) {
      return m;
    } else {
      return new UnmodifiableMultiMap<>(m);
    }
  }

  /**
   * Returns a {@code Collector} that accumulates elements into a {@code Map} whose keys and values are the result of applying the
   * provided mapping functions to the input elements.
   * <p>
   * If the mapped keys contains duplicates (according to {@link Object#equals(Object)}), the value mapping function is applied to
   * each equal element, and the results are added to the values collection.
   * 
   * @param <T>         the type of the input elements
   * @param <K>         the output type of the key mapping function
   * @param <U>         the output type of the value mapping function
   * @param keyMapper   a mapping function to produce keys
   * @param valueMapper a mapping function to produce values
   * @return a {@code Collector} which collects elements into a {@code MultiMap} whose keys and values are the result of applying
   *         mapping functions to the input elements.
   * 
   * @since 1.5
   */
  public static <T, K, U> Collector<T, ?, MultiMap<K, U>> toMultiMap(Function<? super T, ? extends K> keyMapper,
                                                                     Function<? super T, ? extends U> valueMapper) {
    return toMap(keyMapper, valueMapper, (u, v) -> v, () -> new MultiMap<>());
  }

  protected Map<K, LinkedList<V>> paramsMap;

  public MultiMap(final MultiMap<K, V> multiMap) {
    this.paramsMap = new LinkedHashMap<>(multiMap.paramsMap);
  }

  public MultiMap(final Map<K, V> parametersMap) {
    this.paramsMap = parametersMap.entrySet().stream().collect(toMap(Entry::getKey, e -> {
      LinkedList<V> values = new LinkedList<>();
      if (e.getValue() instanceof Collection) {
        values.addAll((Collection<? extends V>) e.getValue());
      } else {
        values.add(e.getValue());
      }
      return values;
    }, (u, v) -> {
      throw new IllegalStateException(String.format("Duplicate key %s", u));
    }, LinkedHashMap::new));
    this.paramsMap = unmodifiableMap(paramsMap);
  }

  public MultiMap() {
    this.paramsMap = new LinkedHashMap<>();
  }

  public MultiMap<K, V> toImmutableMultiMap() {
    if (this instanceof ImmutableMultiMap) {
      return this;
    }
    if (this.isEmpty() && emptyMultiMap() != null) {
      return emptyMultiMap();
    }

    return new ImmutableMultiMap(this.paramsMap);
  }

  @Override
  public int size() {
    return paramsMap.values().stream().reduce(0, (count, list) -> list.size() + count, (count, list) -> count);
  }

  @Override
  public boolean isEmpty() {
    return paramsMap.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return paramsMap.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return paramsMap.values().stream().anyMatch(list -> list.contains(value));
  }

  @Override
  public V get(Object key) {
    final LinkedList<V> values = paramsMap.get(key);
    if (values != null) {
      return values.getFirst();
    }
    return null;
  }

  /**
   * Returns the values to which the specified key is mapped.
   *
   * @param key the key whose associated values are to be returned
   * @return a list of values to which the specified key is mapped, or an empty list if this map contains no mappings for the key
   */
  public List<V> getAll(Object key) {
    LinkedList<V> value = paramsMap.get(key);
    if (value != null) {
      return unmodifiableList(value);
    } else {
      return emptyList();
    }
  }

  @Override
  public V put(K key, V value) {
    LinkedList<V> previousValue = paramsMap.get(key);
    final V previousItem = resolvePreviousItem(previousValue);

    if (previousValue == null) {
      previousValue = new LinkedList<>();
      paramsMap.put(key, previousValue);
    }
    previousValue.add(value);
    return previousItem;
  }

  private V resolvePreviousItem(LinkedList<V> previousValue) {
    if (previousValue == null || previousValue.isEmpty()) {
      return null;
    }
    return previousValue.getFirst();
  }

  /**
   * Associates the specified value with the specified key in this map (optional operation). If the map previously contained
   * mappings for the key, the new values are aggregated to the old.
   *
   * @param key    key with which the specified values are to be associated
   * @param values collection of values to be associated with the specified key
   */
  public void put(K key, Collection<V> values) {
    paramsMap.compute(key, (k, curVal) -> {
      if (curVal == null) {
        return new LinkedList<>(values);
      } else {
        curVal.addAll(values);
        return curVal;
      }
    });
  }

  @Override
  public V remove(Object key) {
    Collection<V> values = paramsMap.remove(key);
    if (values != null) {
      return values.iterator().next();
    }
    return null;
  }

  /**
   * Removes all elements associated with the provided {@code key}.
   *
   * @param key the key of the mappings to remove.
   * @return all values that were associated to the provided {@code key}.
   */
  public List<V> removeAll(Object key) {
    return paramsMap.remove(key);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> aMap) {
    aMap.forEach((key, value) -> {
      LinkedList<V> values = new LinkedList<>();
      values.add(value);
      paramsMap.put(key, values);
    });
  }

  /**
   * Similar to {@link #putAll(Map)}, but instead of putting only the first value of each key, all the values are copied for each
   * key.
   *
   * @param aMultiMap mappings to be stored in this map
   * @throws ClassCastException       if the class of a key or value in the specified map prevents it from being stored in this
   *                                  map
   * @throws NullPointerException     if the specified map is null, or if this map does not permit null keys or values, and the
   *                                  specified map contains null keys or values
   * @throws IllegalArgumentException if some property of a key or value in the specified map prevents it from being stored in
   *                                  this map
   * @since 1.1.1
   */
  public void putAll(MultiMap<? extends K, ? extends V> aMultiMap) {
    aMultiMap.paramsMap.forEach((k, v) -> put(k, (Collection<V>) v));
  }

  @Override
  public void clear() {
    paramsMap.clear();
  }

  @Override
  public Set<K> keySet() {
    return paramsMap.keySet();
  }

  @Override
  public Collection<V> values() {
    List<V> values = new ArrayList<>();
    paramsMap.forEach((key, value) -> values.add(value.getFirst()));
    return values;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    Set<Entry<K, V>> entries = new HashSet<>();
    paramsMap.forEach((key, value) -> entries.add(new AbstractMap.SimpleEntry<>(key, value.getFirst())));
    return entries;
  }

  /**
   * Returns a {@link List} view of the mappings contained in this map, including an entry for each value associated to the same
   * key.
   *
   * @return a list view of the mappings contained in this map
   */
  public List<Entry<K, V>> entryList() {
    // Performance is worse when the initial size is low, so it is only changed when a big list is needed.
    // Also, account for multiple values of the same header by doubling its expected size.
    List<Entry<K, V>> entries = new ArrayList<>(max(10, paramsMap.size() * 2));
    paramsMap.forEach((key, values) -> values.forEach(value -> entries.add(new AbstractMap.SimpleEntry<>(key, value))));
    return entries;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof MultiMap && paramsMap.equals(((MultiMap) o).paramsMap);
  }

  @Override
  public int hashCode() {
    return paramsMap.hashCode();
  }

  public Map<K, ? extends List<V>> toListValuesMap() {
    return unmodifiableMap(paramsMap);
  }

  @Override
  public String toString() {
    return "MultiMap{" + Arrays.toString(paramsMap.entrySet().toArray()) + '}';
  }

  private static class ImmutableMultiMap<K, V> extends MultiMap<K, V> {

    private static final long serialVersionUID = -4581793201929981747L;

    ImmutableMultiMap(final Map<K, V> parametersMap) {
      super(parametersMap);
    }

    @Override
    public MultiMap<K, V> toImmutableMultiMap() {
      return this;
    }
  }

  public static class StringMultiMap extends MultiMap<String, String> implements DataTypeAware {

    private static final StringMultiMap EMPTY_STRING_MAP = new StringMultiMap().toImmutableMultiMap();

    private static final long serialVersionUID = 3153407829619876577L;

    public StringMultiMap() {
      super();
    }

    public StringMultiMap(final MultiMap<String, String> parametersMap) {
      super(parametersMap);
    }

    public StringMultiMap(final Map<String, String> parametersMap) {
      super(parametersMap);
    }

    @Override
    public DataType getDataType() {
      return MULTI_MAP_STRING_STRING;
    }

    @Override
    public StringMultiMap toImmutableMultiMap() {
      if (this.isEmpty() && EMPTY_STRING_MAP != null) {
        return EMPTY_STRING_MAP;
      }
      return new ImmutableStringMultiMap(this.paramsMap);
    }

  }

  private static class ImmutableStringMultiMap extends StringMultiMap implements DataTypeAware {

    private static final long serialVersionUID = -4581793201929981747L;

    ImmutableStringMultiMap(final Map parametersMap) {
      super(parametersMap);
    }

    @Override
    public DataType getDataType() {
      return MULTI_MAP_STRING_STRING;
    }

    @Override
    public StringMultiMap toImmutableMultiMap() {
      return this;
    }
  }

  private static class UnmodifiableMultiMap<K, V> extends MultiMap<K, V> {

    private static final long serialVersionUID = 6798199484376351419L;

    private final MultiMap<K, V> m;

    public UnmodifiableMultiMap(MultiMap<K, V> m) {
      this.m = m;
    }

    @Override
    public MultiMap<K, V> toImmutableMultiMap() {
      return m.toImmutableMultiMap();
    }

    @Override
    public int size() {
      return m.size();
    }

    @Override
    public boolean isEmpty() {
      return m.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
      return m.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
      return m.containsValue(value);
    }

    @Override
    public V get(Object key) {
      return m.get(key);
    }

    @Override
    public List<V> getAll(Object key) {
      return m.getAll(key);
    }

    @Override
    public V put(K key, V value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void put(K key, Collection<V> values) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
      throw new UnsupportedOperationException();
    }

    @Override
    public List<V> removeAll(Object key) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> aMap) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(MultiMap<? extends K, ? extends V> aMultiMap) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
      return unmodifiableSet(m.keySet());
    }

    @Override
    public Collection<V> values() {
      return unmodifiableCollection(m.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
      return unmodifiableSet(m.entrySet());
    }

    @Override
    public List<Entry<K, V>> entryList() {
      return unmodifiableList(m.entryList());
    }

    @Override
    public boolean equals(Object o) {
      return m.equals(o);
    }

    @Override
    public int hashCode() {
      return m.hashCode();
    }

    @Override
    public Map<K, ? extends List<V>> toListValuesMap() {
      return m.toListValuesMap();
    }

    @Override
    public String toString() {
      return m.toString();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
      return m.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
      m.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V replace(K key, V value) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      throw new UnsupportedOperationException();
    }

  }
}
