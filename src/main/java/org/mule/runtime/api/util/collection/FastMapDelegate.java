/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.io.Serializable;
import java.util.Map;

abstract class FastMapDelegate<K, V> implements Map<K, V>, Serializable {

  protected transient V previousValue;

  abstract FastMapDelegate<K, V> fastPut(K key, V value);

  abstract FastMapDelegate<K, V> fastRemove(Object key);

  abstract FastMapDelegate<K, V> copy();

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
