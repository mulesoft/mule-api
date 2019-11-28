/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class UnmodifiableSetAdapter<T> implements Set<T> {

  private Collection<T> delegate;

  public UnmodifiableSetAdapter(Collection<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public int size() {
    return delegate.size();
  }

  @Override
  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return delegate.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return delegate.iterator();
  }

  @Override
  public Object[] toArray() {
    return delegate.toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    return delegate.toArray(a);
  }

  private UnsupportedOperationException unmodifiable() {
    return new UnsupportedOperationException("This set is unmodifiable");
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
    return delegate.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    throw unmodifiable();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw unmodifiable();
  }

  @Override
  public boolean removeIf(Predicate<? super T> filter) {
    throw unmodifiable();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw unmodifiable();
  }

  @Override
  public void clear() {
    throw unmodifiable();
  }

  @Override
  public boolean equals(Object o) {
    return delegate.equals(o);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public Spliterator<T> spliterator() {
    return delegate.spliterator();
  }

  @Override
  public Stream<T> stream() {
    return delegate.stream();
  }

  @Override
  public Stream<T> parallelStream() {
    return delegate.parallelStream();
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    delegate.forEach(action);
  }
}
