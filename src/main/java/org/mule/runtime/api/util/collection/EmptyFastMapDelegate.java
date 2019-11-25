/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import static java.util.Collections.emptySet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

class EmptyFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  public EmptyFastMapDelegate(Supplier<Map<K, V>> overflowDelegateFactory, V previousValue) {
    super(overflowDelegateFactory);
    this.previousValue = previousValue;
  }

  @Override
  public FastMapDelegate<K, V> fastPut(K key, V value) {
    return new UniFastMapDelegate<>(overflowDelegateFactory, new FastMapEntry<>(key, value), null);
  }

  @Override
  public FastMapDelegate<K, V> fastRemove(Object key) {
    previousValue = null;
    return this;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public boolean containsKey(Object key) {
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    return false;
  }

  @Override
  public V get(Object key) {
    return null;
  }

  @Override
  public Set<K> keySet() {
    return emptySet();
  }

  @Override
  public Collection<V> values() {
    return emptySet();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return emptySet();
  }
}
