/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
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
 * {@link SmallMapDelegate} implementation for maps that have six entries
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class HexSmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  private Entry<K, V> entry1;
  private Entry<K, V> entry2;
  private Entry<K, V> entry3;
  private Entry<K, V> entry4;
  private Entry<K, V> entry5;
  private Entry<K, V> entry6;

  public HexSmallMapDelegate(Entry<K, V> entry1,
                             Entry<K, V> entry2,
                             Entry<K, V> entry3,
                             Entry<K, V> entry4,
                             Entry<K, V> entry5,
                             Entry<K, V> entry6,
                             V previousValue) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.entry3 = entry3;
    this.entry4 = entry4;
    this.entry5 = entry5;
    this.entry6 = entry6;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 6;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean containsKey(Object key) {
    return Objects.equals(entry1.getKey(), key)
        || Objects.equals(entry2.getKey(), key)
        || Objects.equals(entry3.getKey(), key)
        || Objects.equals(entry4.getKey(), key)
        || Objects.equals(entry5.getKey(), key)
        || Objects.equals(entry6.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value)
        || Objects.equals(entry2.getValue(), value)
        || Objects.equals(entry3.getValue(), value)
        || Objects.equals(entry4.getValue(), value)
        || Objects.equals(entry5.getValue(), value)
        || Objects.equals(entry6.getValue(), value);
  }

  @Override
  public V get(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return entry1.getValue();
    } else if (Objects.equals(entry2.getKey(), key)) {
      return entry2.getValue();
    } else if (Objects.equals(entry3.getKey(), key)) {
      return entry3.getValue();
    } else if (Objects.equals(entry4.getKey(), key)) {
      return entry4.getValue();
    } else if (Objects.equals(entry5.getKey(), key)) {
      return entry5.getValue();
    } else if (Objects.equals(entry6.getKey(), key)) {
      return entry6.getValue();
    } else {
      return null;
    }
  }

  @Override
  public Set<K> keySet() {
    return new UnmodifiableSet<K>() {

      @Override
      protected K get(int index) {
        switch (index) {
          case 0:
            return entry1.getKey();
          case 1:
            return entry2.getKey();
          case 2:
            return entry3.getKey();
          case 3:
            return entry4.getKey();
          case 4:
            return entry5.getKey();
        }
        return entry6.getKey();
      }

      @Override
      public int size() {
        return 6;
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
        switch (index) {
          case 0:
            return entry1.getValue();
          case 1:
            return entry2.getValue();
          case 2:
            return entry3.getValue();
          case 3:
            return entry4.getValue();
          case 4:
            return entry5.getValue();
        }
        return entry6.getValue();
      }

      @Override
      public int size() {
        return 6;
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
        switch (index) {
          case 0:
            return entry1;
          case 1:
            return entry2;
          case 2:
            return entry3;
          case 3:
            return entry4;
          case 4:
            return entry5;
        }
        return entry6;
      }

      @Override
      public int size() {
        return 6;
      }

      @Override
      public boolean contains(Object o) {
        return Objects.equals(o, entry1)
            || Objects.equals(o, entry2)
            || Objects.equals(o, entry3)
            || Objects.equals(o, entry4)
            || Objects.equals(o, entry5)
            || Objects.equals(o, entry6);
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
    } else if (Objects.equals(entry3.getKey(), key)) {
      previousValue = entry3.getValue();
      entry3 = new SmallMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry4.getKey(), key)) {
      previousValue = entry4.getValue();
      entry4 = new SmallMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry5.getKey(), key)) {
      previousValue = entry5.getValue();
      entry5 = new SmallMapEntry<>(key, value);
      return this;
    } else if (Objects.equals(entry6.getKey(), key)) {
      previousValue = entry6.getValue();
      entry6 = new SmallMapEntry<>(key, value);
      return this;
    } else {
      Map<K, V> overflow = new HashMap<>(this);
      overflow.put(key, value);
      return new NSmallMapDelegate<>(overflow, null);
    }
  }

  @Override
  public SmallMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry2, entry3, entry4, entry5, entry6, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry1, entry3, entry4, entry5, entry6, entry2.getValue());
    } else if (Objects.equals(entry3.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry1, entry2, entry4, entry5, entry6, entry3.getValue());
    } else if (Objects.equals(entry4.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry1, entry2, entry3, entry5, entry6, entry4.getValue());
    } else if (Objects.equals(entry5.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry1, entry2, entry3, entry4, entry6, entry5.getValue());
    } else if (Objects.equals(entry6.getKey(), key)) {
      return new PentaSmallMapDelegate<>(entry1, entry2, entry3, entry4, entry5, entry6.getValue());
    } else {
      previousValue = null;
      return this;
    }
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new HexSmallMapDelegate<>(entry1, entry2, entry3, entry4, entry5, entry6, null);
  }
}
