/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.util.collection;

import java.util.Map;

/**
 * Immutable {@link Map.Entry} implementation.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 *
 * @since 1.0
 */
public class ImmutableEntry<K, V> implements Map.Entry<K, V> {

  private final Map.Entry<K, V> entry;

  public ImmutableEntry(Map.Entry<K, V> entry) {
    this.entry = entry;
  }

  @Override
  public K getKey() {
    return entry.getKey();
  }

  @Override
  public V getValue() {
    return entry.getValue();
  }

  @Override
  public V setValue(V value) {
    throw new UnsupportedOperationException("It's not possible to update a map entry result of a map iteration");
  }
}
