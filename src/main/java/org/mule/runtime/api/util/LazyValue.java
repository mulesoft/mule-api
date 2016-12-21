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
 * This class is not thread-safe. Concurrently invoking {@link #get()} might result on the value being computed
 * several times
 *
 * @param <T> the generic type of the provided value
 * @since 1.0
 */
public class LazyValue<T> {

  private T value;
  private final Supplier<T> supplier;

  public LazyValue(Supplier<T> supplier) {
    checkArgument(supplier != null, "supplier cannot be null");
    this.supplier = supplier;
  }

  public T get() {
    if (value == null) {
      value = supplier.get();
    }
    return value;
  }
}
