/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Base class for delegate obejcts used as a backing data structure for a {@link SmallMap}.
 * <p>
 * Notice that this class implements the {@link Map} interface even though many of its methods throw
 * {@link UnsupportedOperationException}. This is an optimization to speed up copy operations in which the target object is not
 * another {@link SmallMap}.
 *
 * @param <K> The generic type of the keys
 * @param <V> the generic type of the values
 * @since 1.3.0
 */
abstract class SmallMapDelegate<K, V> implements Map<K, V>, Serializable {

  /**
   * This is used for operations such as {@link Map#put(Object, Object)} in which a previous value might be returned. However,
   * because that put operation might change the implementation of the underlying delegate, this field is used to temporarily
   * store such value.
   */
  protected transient V previousValue;

  /**
   * Method to be used to add an entry into the {@link SmallMap}. This operation returns another {@link SmallMapDelegate} which is
   * the one that the owning {@link SmallMap} should continue to use
   *
   * @param key   the key to be added
   * @param value the value to be added
   * @return the {@link SmallMapDelegate} to be used from now on
   */
  abstract SmallMapDelegate<K, V> fastPut(K key, V value);

  /**
   * Method to be used to remove an entry into the {@link SmallMap}. This operation returns another {@link SmallMapDelegate} which
   * is the one that the owning {@link SmallMap} should continue to use
   *
   * @param key the key to be added
   * @return the {@link SmallMapDelegate} to be used from now on
   */
  abstract SmallMapDelegate<K, V> fastRemove(Object key);

  /**
   * Returns a shallow copy of {@code this} instance
   *
   * @return a shallow copy
   */
  abstract SmallMapDelegate<K, V> copy();

  /**
   * Returns the contents of the {@link #previousValue} field, after which, said field is nullified.
   *
   * @return a nullable previous value.
   */
  public V getPreviousValue() {
    V retVal = previousValue;
    previousValue = null;

    return retVal;
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

  /**
   * An implementation of an unmodifiable {@link Set} designed to power methods such as {@link Map#entrySet()},
   * {@link Map#keySet()} or {@link Map#values()} in the most optimized way possible, without the need to copy data structures.
   *
   * @param <T> the generic type of the Set's elements
   */
  protected abstract class UnmodifiableSet<T> implements Set<T> {

    @Override
    public boolean isEmpty() {
      return false;
    }

    /**
     * Returns the element of the given position.
     *
     * @param index the index of the element to be returned
     * @return an element
     */
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
          int next = index++;
          if (next >= size) {
            throw new NoSuchElementException();
          }
          return get(next);
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

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof Set)) {
        return false;
      }

      Collection<?> c = (Collection<?>) obj;
      if (c.size() != size()) {
        return false;
      }
      return containsAll(c);
    }

    @Override
    public int hashCode() {
      int h = 0;
      Iterator<T> i = iterator();
      while (i.hasNext()) {
        T obj = i.next();
        if (obj != null) {
          h += obj.hashCode();
        }
      }
      return h;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder("{");
      boolean first = true;
      for (T item : this) {
        if (!first) {
          builder.append(", ");
        }

        builder.append(item);
        first = false;
      }

      return builder.append("}").toString();
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("{");
    boolean first = true;
    for (Entry<K, V> entry : entrySet()) {
      if (!first) {
        builder.append(", ");
      }

      builder.append(entry.toString());
      first = false;
    }

    return builder.append('}').toString();
  }
}
