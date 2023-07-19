/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Objects.hash;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Implementation of {@link Entry} to be used by {@link SmallMap} implementations
 *
 * @param <K> the generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
class SmallMapEntry<K, V> implements Entry<K, V>, Serializable {

  final K key;
  V value;

  SmallMapEntry(K key, V value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public K getKey() {
    return key;
  }

  @Override
  public V getValue() {
    return value;
  }

  @Override
  public V setValue(V value) {
    V old = this.value;
    this.value = value;
    return old;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Entry) {
      Entry<K, V> other = (Entry<K, V>) obj;
      return Objects.equals(this.key, other.getKey()) && Objects.equals(this.value, other.getValue());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return hash(key, value);
  }

  @Override
  public String toString() {
    return key + "=" + value;
  }
}
