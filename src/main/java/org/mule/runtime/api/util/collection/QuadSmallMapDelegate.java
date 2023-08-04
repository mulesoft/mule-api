/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * {@link SmallMapDelegate} implementation for maps that have four entries
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class QuadSmallMapDelegate<K, V> extends SmallMapDelegate<K, V> {

  private Entry<K, V> entry1;
  private Entry<K, V> entry2;
  private Entry<K, V> entry3;
  private Entry<K, V> entry4;

  public QuadSmallMapDelegate(Entry<K, V> entry1,
                              Entry<K, V> entry2,
                              Entry<K, V> entry3,
                              Entry<K, V> entry4,
                              V previousValue) {
    this.entry1 = entry1;
    this.entry2 = entry2;
    this.entry3 = entry3;
    this.entry4 = entry4;
    this.previousValue = previousValue;
  }

  @Override
  public int size() {
    return 4;
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
        || Objects.equals(entry4.getKey(), key);
  }

  @Override
  public boolean containsValue(Object value) {
    return Objects.equals(entry1.getValue(), value)
        || Objects.equals(entry2.getValue(), value)
        || Objects.equals(entry3.getValue(), value)
        || Objects.equals(entry4.getValue(), value);
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
        }
        return entry4.getKey();
      }

      @Override
      public int size() {
        return 4;
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
        }
        return entry4.getValue();
      }

      @Override
      public int size() {
        return 4;
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
        }
        return entry4;
      }

      @Override
      public int size() {
        return 4;
      }

      @Override
      public boolean contains(Object o) {
        return Objects.equals(o, entry1)
            || Objects.equals(o, entry2)
            || Objects.equals(o, entry3)
            || Objects.equals(o, entry4);
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
    } else {
      return new PentaSmallMapDelegate(entry1, entry2, entry3, entry4, new SmallMapEntry<>(key, value), null);
    }
  }

  @Override
  public SmallMapDelegate<K, V> fastRemove(Object key) {
    if (Objects.equals(entry1.getKey(), key)) {
      return new TriSmallMapDelegate<>(entry2, entry3, entry4, entry1.getValue());
    } else if (Objects.equals(entry2.getKey(), key)) {
      return new TriSmallMapDelegate<>(entry1, entry3, entry4, entry2.getValue());
    } else if (Objects.equals(entry3.getKey(), key)) {
      return new TriSmallMapDelegate<>(entry1, entry2, entry4, entry3.getValue());
    } else if (Objects.equals(entry4.getKey(), key)) {
      return new TriSmallMapDelegate<>(entry1, entry2, entry3, entry4.getValue());
    } else {
      previousValue = null;
      return this;
    }
  }

  @Override
  SmallMapDelegate<K, V> copy() {
    return new QuadSmallMapDelegate<>(entry1, entry2, entry3, entry4, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuadSmallMapDelegate<?, ?> that = (QuadSmallMapDelegate<?, ?>) o;
    return Objects.equals(entry1, that.entry1) &&
        Objects.equals(entry2, that.entry2) &&
        Objects.equals(entry3, that.entry3) &&
        Objects.equals(entry4, that.entry4);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entry1, entry2, entry3, entry4);
  }
}
