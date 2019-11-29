/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

abstract class SmallMapDelegate<K, V> implements Map<K, V>, Serializable {

  protected transient V previousValue;

  abstract SmallMapDelegate<K, V> fastPut(K key, V value);

  abstract SmallMapDelegate<K, V> fastRemove(Object key);

  abstract SmallMapDelegate<K, V> copy();

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

  protected abstract class UnmodifiableSet<T> implements Set<T> {

    @Override
    public boolean isEmpty() {
      return false;
    }

    protected abstract T get(int index);

    @Override
    public Iterator<T> iterator() {
      return new Iterator<T>() {

        private int index = 0;
        private final int size = size();

        @Override
        public boolean hasNext() {
          return index < size;
        }

        @Override
        public T next() {
          return get(index++);
        }

        @Override
        public void remove() {
          throw unmodifiable();
        }
      };
    }

    @Override
    public boolean add(T t) {
      throw unmodifiable();
    }

    @Override
    public boolean remove(Object o) {
      throw unmodifiable();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      for (Object item : c) {
        if (!contains(item)) {
          return false;
        }
      }

      return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
      throw unmodifiable();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      throw unmodifiable();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      throw unmodifiable();
    }

    @Override
    public void clear() {
      throw unmodifiable();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
      throw unmodifiable();
    }

    private UnsupportedOperationException unmodifiable() {
      return new UnsupportedOperationException("This set is unmodifiable");
    }

    @Override
    public Object[] toArray() {
      Object[] array = new Object[size()];
      int i = 0;
      for (Object item : this) {
        array[i++] = item;
      }

      return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
      Iterator<T> it = iterator();
      for (int i = 0; i < a.length; i++) {
        a[i] = it.hasNext() ? (T1) it.next() : null;
      }

      return a;
    }
  }
}
