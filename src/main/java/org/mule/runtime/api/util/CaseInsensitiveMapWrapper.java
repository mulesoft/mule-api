/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.runtime.api.util.collection.SmallMap;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a Map from String to {@link T} where the key's case is not taken into account when looking for it, but remembered
 * when the key set is retrieved from the map. The backing map used will be the {@link Map} instance passed to the constructor.
 * This allows to make case insensitive different kinds of Map implementations, particularly a ConcurrentHashMap, used to achieve
 * high concurrence.
 * <p/>
 * When a key/value pair is put in the map the key case is remembered so when the key set or the entry set is retrieved the
 * correct case is returned. This is useful to store, for example, camel case keys. However, two keys that only differ in their
 * case will be assumed to be the same key and only one value (the last) will be kept. Note: as this map uses a provided class to
 * create the backing map, key rewrite is not ensured. It is possible that when redefining a value associated to a key, the key
 * case won't be overwritten and the already existing key case will remains in the key set and entry set.
 *
 * @param <T> The class of the values referenced in the map.
 *
 * @since 1.0
 */
public class CaseInsensitiveMapWrapper<T> extends AbstractMap<String, T> implements Serializable {

  private static final long serialVersionUID = 8534959274607933747L;

  protected final Map<CaseInsensitiveMapKey, T> baseMap;

  /**
   * Creates a new instance using an existing map as backing map. Said map should be empty.
   *
   * @param map existing map
   */
  public CaseInsensitiveMapWrapper(Map map) {
    this(map, true);
  }

  private CaseInsensitiveMapWrapper(Map map, boolean requireEmpty) {
    if (requireEmpty) {
      checkArgument(map.isEmpty(), "Cannot create case insensitive map from a non empty one.");
    }

    baseMap = map;
  }

  /**
   * Creates a new instance using a {@link HashMap} as backing map.
   */
  public CaseInsensitiveMapWrapper() {
    this(new HashMap());
  }

  /**
   * Creates a shallow copy of this instance. This is the recommended way of creating copy instances as this is optimized and
   * usually much faster than using the {@link CaseInsensitiveMapWrapper#CaseInsensitiveMapWrapper(Map)} constructor.
   *
   * @return a shallow copy of {@code this} instance
   * @since 1.3.0
   */
  public CaseInsensitiveMapWrapper<T> copy() {
    return new CaseInsensitiveMapWrapper<>(SmallMap.copy(baseMap), false);
  }

  @Override
  public int size() {
    return baseMap.size();
  }

  @Override
  public boolean isEmpty() {
    return baseMap.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return baseMap.containsKey(CaseInsensitiveMapKey.keyFor(key));
  }

  @Override
  public boolean containsValue(Object value) {
    return baseMap.containsValue(value);
  }

  @Override
  public T get(Object key) {
    return baseMap.get(CaseInsensitiveMapKey.keyFor(key));
  }

  @Override
  public T put(String key, T value) {
    return baseMap.put(CaseInsensitiveMapKey.keyFor(key), value);
  }

  @Override
  public T remove(Object key) {
    return baseMap.remove(CaseInsensitiveMapKey.keyFor(key));
  }

  @Override
  public void putAll(Map<? extends String, ? extends T> other) {
    if (other instanceof CaseInsensitiveMapWrapper) {
      baseMap.putAll(((CaseInsensitiveMapWrapper) other).baseMap);
    } else {
      for (Map.Entry<? extends String, ? extends T> otherEntry : other.entrySet()) {
        put(otherEntry.getKey(), otherEntry.getValue());
      }
    }
  }

  @Override
  public void clear() {
    baseMap.clear();
  }

  @Override
  public Set<String> keySet() {
    return new KeySet(baseMap.keySet());
  }

  @Override
  public Collection<T> values() {
    return baseMap.values();
  }

  @Override
  public Set<Entry<String, T>> entrySet() {
    return new EntrySet<>(baseMap.entrySet());
  }

  protected static class CaseInsensitiveMapKey implements Serializable {

    private static final long serialVersionUID = -4964071931663826261L;

    private static final ConcurrentMap<Object, CaseInsensitiveMapKey> cache = new ConcurrentHashMap<>();
    private static final AtomicBoolean usingCache = new AtomicBoolean(true);
    private static final int MAX_CACHE_SIZE = 1000;
    private static final int MAX_KEY_SIZE = 100;

    private final String key;
    private final String keyLowerCase;
    private final int keyHash;

    public static CaseInsensitiveMapKey keyFor(Object key) {
      if (!usingCache.get() || key.toString().length() > MAX_KEY_SIZE) {
        return new CaseInsensitiveMapKey(key);
      }

      CaseInsensitiveMapKey value = cache.get(key);
      if (value == null) {
        value = cache.computeIfAbsent(key, CaseInsensitiveMapKey::new);

        // we do the size check only if the value was not present and needed to be computed
        // being this a ConcurrentHashMap, getting the size is not as cheap as it looks
        if (cache.size() > MAX_CACHE_SIZE) {
          usingCache.set(false);
          cache.clear();
        }
      }

      return value;
    }

    private CaseInsensitiveMapKey(Object key) {
      this.key = key.toString();
      keyLowerCase = this.key.toLowerCase();
      keyHash = keyLowerCase.hashCode();
    }

    public String getKey() {
      return key;
    }

    public String getKeyLowerCase() {
      return keyLowerCase;
    }

    @Override
    public int hashCode() {
      return keyHash;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      return (obj instanceof CaseInsensitiveMapKey) && keyLowerCase.equals(((CaseInsensitiveMapKey) obj).keyLowerCase);
    }
  }


  private static class KeySet extends AbstractConverterSet<CaseInsensitiveMapKey, String> {

    public KeySet(Set<CaseInsensitiveMapKey> keys) {
      super(keys);
    }

    @Override
    protected Iterator<String> createIterator(Set<CaseInsensitiveMapKey> keys) {
      return new KeyIterator(keys);
    }

    @Override
    protected CaseInsensitiveMapKey keyFor(Object o) {
      return CaseInsensitiveMapKey.keyFor(o);
    }
  }

  private static class EntrySet<T> extends AbstractConverterSet<Entry<CaseInsensitiveMapKey, T>, Entry<String, T>> {

    public EntrySet(Set<Map.Entry<CaseInsensitiveMapKey, T>> entries) {
      super(entries);
    }

    @Override
    protected Iterator<Entry<String, T>> createIterator(Set<Entry<CaseInsensitiveMapKey, T>> entries) {
      return new EntryIterator<>(entries);
    }

    @Override
    protected Entry<CaseInsensitiveMapKey, T> keyFor(Object o) {
      if (o instanceof Entry) {
        Entry<CaseInsensitiveMapKey, T> o2 = (Entry<CaseInsensitiveMapKey, T>) o;
        return new AbstractMap.SimpleImmutableEntry<>(CaseInsensitiveMapKey.keyFor(o2.getKey()), o2.getValue());
      }

      return null;
    }
  }

  protected static abstract class AbstractConverterSet<A, B> extends AbstractSet<B> {

    private final Set<A> aSet;

    public AbstractConverterSet(Set<A> set) {
      this.aSet = set;
    }

    @Override
    public int size() {
      return aSet.size();
    }

    @Override
    public Iterator<B> iterator() {
      return createIterator(aSet);
    }

    @Override
    public boolean contains(Object o) {
      return o != null && aSet.contains(keyFor(o));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      return c.stream().map(o -> keyFor(o)).allMatch(o -> o != null && aSet.contains(o));
    }

    @Override
    public boolean remove(Object o) {
      return o != null && aSet.contains(keyFor(o));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return c.stream().map(o -> keyFor(o)).allMatch(o -> o != null && aSet.remove(o));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      return aSet.retainAll(c.stream().map(o -> keyFor(o)).collect(toSet()));
    }

    @Override
    public void clear() {
      aSet.clear();
    }

    @Override
    public boolean isEmpty() {
      return aSet.isEmpty();
    }

    protected A keyFor(Object o) {
      return (A) o;
    }

    protected abstract Iterator<B> createIterator(Set<A> aSet);
  }


  private static class KeyIterator extends AbstractConverterIterator<CaseInsensitiveMapKey, String> {

    public KeyIterator(Set<CaseInsensitiveMapKey> keys) {
      super(keys);
    }

    @Override
    protected String convert(CaseInsensitiveMapKey next) {
      return next.getKey();
    }
  }

  private static class EntryIterator<T> extends AbstractConverterIterator<Entry<CaseInsensitiveMapKey, T>, Entry<String, T>> {

    public EntryIterator(Set<Map.Entry<CaseInsensitiveMapKey, T>> entries) {
      super(entries);
    }

    @Override
    protected Entry<String, T> convert(Entry<CaseInsensitiveMapKey, T> next) {
      return new AbstractMap.SimpleEntry<>(next.getKey().getKey(), next.getValue());
    }
  }

  private static abstract class AbstractConverterIterator<A, B> implements Iterator<B> {

    private final Iterator<A> aIterator;

    public AbstractConverterIterator(Set<A> set) {
      aIterator = set.iterator();
    }

    @Override
    public boolean hasNext() {
      return aIterator.hasNext();
    }

    @Override
    public final void remove() {
      aIterator.remove();
    }

    @Override
    public final B next() {
      return convert(aIterator.next());
    }

    protected abstract B convert(A next);
  }

  /**
   * Returns this map as a case sensitive map.
   *
   * @return case-sensitive map
   */
  public Map<String, T> asCaseSensitiveMap() {
    return baseMap.entrySet().stream().collect(toMap(entry -> entry.getKey().getKey(), entry -> entry.getValue()));
  }

}
