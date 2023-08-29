/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static java.lang.System.identityHashCode;
import org.mule.api.annotation.NoExtend;

/**
 * An object which holds a reference to a given {@link #value}.
 * <p>
 * Because this class represents a reference to such value instead of just being a simple holder, the {@link #equals(Object)} has
 * been redefined to test that the two values are actually the same ('{@code ==}'). Consistently, the {@link #hashCode()} method
 * is defined to return the value's identity hash code.
 * <p>
 * The above makes this class useful for cases in which you need to build a set of unique references, without depending on the
 * actual {@link #equals(Object)} and {@link #hashCode()} implementations.
 * <p>
 * {@code null} references are also supported.
 *
 * @param <T> the generic type of the referenced value
 * @since 1.0
 */
@NoExtend
public class Reference<T> {

  private T value;

  /**
   * Creates a new instance which references {@code null}
   */
  public Reference() {
    this(null);
  }

  /**
   * Creates a new instance
   *
   * @param value the referenced value
   */
  public Reference(T value) {
    set(value);
  }

  /**
   * @return the referenced value
   */
  public T get() {
    return value;
  }

  public T set(T value) {
    T oldValue = this.value;
    this.value = value;

    return oldValue;
  }

  /**
   * @param obj the compared object
   * @return {@code true} if {@code obj} is a {@link Reference} which references the same value
   */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Reference && value == ((Reference) obj).value;
  }

  /**
   * @return the value's hash code
   */
  @Override
  public int hashCode() {
    return identityHashCode(value);
  }
}
