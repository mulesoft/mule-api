/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * {@link SmallMapDelegate} implementation for maps that have two entries
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class BiSmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  private Entry<K, V> entry1;
  private Entry<K, V> entry2;

  public BiSmallMapDelegate(Entry<K, V> entry1,
                            Entry<K, V> entry2,
                            V previousValue) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 2;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return Objects.equals(entry1.getKey(), key) || Objects.equals(entry2.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value) || Objects.equals(entry2.getValue(), value);
  }

  @Override
  public V get(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return entry1.getValue();
    } else if (Objects.equals(entry2.getKey(), key)) {
      return entry2.getValue();
    } else {
      return null;
    }
  }

  @Override
  public Set<K> keySet() {
    return new UnmodifiableSet<K>() {

      @Override
      protected K get(int index) {
        return index == 0 ? entry1.getKey() : entry2.getKey();
      }

      @Override
      public int size() {
        return 2;
      }

      @Override
      public boolean contains(Object o) {
        return containsKey(o);
      }
    };
  }

  @Override
  public Collection<V> values() {
    return new UnmodifiableSet<V>() {

      @Override
      protected V get(int index) {
        return index == 0 ? entry1.getValue() : entry2.getValue();
      }

      @Override
      public int size() {
        return 2;
      }

      @Override
      public boolean contains(Object o) {
        return containsValue(o);
      }
    };
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return new UnmodifiableSet<Entry<K, V>>() {

      @Override
      protected Entry<K, V> get(int index) {
        return index == 0 ? entry1 : entry2;
      }

      @Override
      public int size() {
        return 2;
      }

      @Override
      public boolean contains(Object o) {
        return Objects.equals(o, entry1) || Objects.equals(o, entry2);
      }
    };
  }

  @Override
  public SmallMapDelegate<K, V> fastPut(K key, V value) {
    if (Objects.equals(entry1.getKey(), key)) {
      previousValue = entry1.getValue();
      entry1 = new SmallMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry2.getKey(), key)) {
      previousValue = entry2.getValue();
      entry2 = new SmallMapEntry<>(key, value);
      return this;
    } else {
      return new TriSmallMapDelegate<>(entry1, entry2, new SmallMapEntry<>(key, value), null);
    }
  }

  @Override
  public SmallMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new UniSmallMapDelegate<>(entry2, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new UniSmallMapDelegate<>(entry1, entry2.getValue());
    } else {
      previousValue = null;
      return this;
    }
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new BiSmallMapDelegate<>(entry1, entry2, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BiSmallMapDelegate<?, ?> that = (BiSmallMapDelegate<?, ?>) o;
    return Objects.equals(entry1, that.entry1) && Objects.equals(entry2, that.entry2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entry1, entry2);
  }
}
