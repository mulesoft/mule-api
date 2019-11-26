/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class NFastMapDelegate<K, V> extends FastMapDelegate<K, V> {

  private final Map<K, V> delegate;

  public NFastMapDelegate(Map<K, V> delegate, V previousValue) {
    this.delegate = delegate;
    this.previousValue = previousValue;
  }

  @Override
  FastMapDelegate<K, V> fastPut(K key, V value) {
    previousValue = delegate.put(key, value);
    return this;
  }

  @Override
  FastMapDelegate<K, V> fastRemove(Object key) {
    previousValue = delegate.remove(key);
    if (delegate.size() > 5) {
      return this;
    }

    Iterator<Entry<K, V>> iterator = entrySet().iterator();
    return new PentaFastMapDelegate<>(iterator.next(),
                                      iterator.next(),
                                      iterator.next(),
                                      iterator.next(),
                                      iterator.next(),
                                      previousValue);
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return false;
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
  FastMapDelegate<K, V> copy() {
    return new NFastMapDelegate<>(new HashMap<>(delegate), null);
  }
}
