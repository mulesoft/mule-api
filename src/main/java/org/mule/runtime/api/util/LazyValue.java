/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.function.Supplier;

/**
 * Provides a value which is lazily computed through a {@link Supplier} received in the constructor.
 * <p>
 * The value is only computed on the first invokation of {@link #get()}. Subsequent calls to such method
 * will always return the same value.
 * <p>
 * This class is thread-safe. When invoking {@link #get()}, it is guaranteed that the value will be initialised
 * only once. However, the provided supplier is not required to be thread safe.
 * several times
 *
 * @param <T> the generic type of the provided value
 * @since 1.0
 */
public class LazyValue<T> {

  private T value;
  private Supplier<T> valueSupplier;

  public LazyValue(Supplier<T> supplier) {
    checkArgument(supplier != null, "supplier cannot be null");
    valueSupplier = () -> {
      synchronized (LazyValue.this) {
        if (value == null) {
          value = supplier.get();
          valueSupplier = () -> value;
        }

        return value;
      }
    };
  }

  /**
   * Returns the lazy value. If the value has not yet been computed, then it does so
   *
   * @return the lazy value
   */
  public T get() {
    return valueSupplier.get();
  }

  /**
   * @return Whether {@link #get()} has already been called on {@code this} instance or not
   */
  public boolean isInitialised() {
    return value != null;
  }
}
