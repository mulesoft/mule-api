/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.util.Map;
import java.util.function.Supplier;

abstract class FastMapDelegate<K, V> implements Map<K, V> {

  protected final Supplier<Map<K, V>> overflowDelegateFactory;

  protected V previousValue;

  FastMapDelegate(Supplier<Map<K, V>> overflowDelegateFactory) {
    this.overflowDelegateFactory = overflowDelegateFactory;
  }

  abstract FastMapDelegate<K, V> fastPut(K key, V value);

  abstract FastMapDelegate<K, V> fastRemove(Object key);

  public V getPreviousValue() {
    return previousValue;
  }

  @Override
  public final V put(K key, V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final V remove(Object key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final void putAll(Map<? extends K, ? extends V> m) {
    throw new UnsupportedOperationException();
  }

  @Override
  public final void clear() {
    throw new UnsupportedOperationException();
  }

}
