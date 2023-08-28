/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides a value which may be lazily computed.
 * <p>
 * The value is only computed on the first invokation of {@link #get()}. Subsequent calls to such method will always return the
 * same value.
 * <p>
 * This class is thread-safe. When invoking {@link #get()}, it is guaranteed that the value will be computed only once.
 *
 * @param <T> the generic type of the provided value
 * @since 1.0
 */
public class LazyValue<T> implements Supplier<T> {

  // All LazyValue attributes are declared transient to allow child classes that implement the Serializable interface
  // to add custom logic for serialization and not depend on the default serialization logic for non-transient fields.
  protected transient volatile boolean initialized = false;
  protected transient T value;
  protected transient Supplier<T> valueSupplier;

  /**
   * no-arg constructor used to allow serialization of child classes.
   *
   * @since 1.2.0
   */
  public LazyValue() {
    this.valueSupplier = () -> null;
  }

  /**
   * Creates a new instance which lazily obtains its value from the given {@code supplier}. It is guaranteed that
   * {@link Supplier#get()} will only be invoked once. Because this class is thread-safe, the supplier is not required to be.
   *
   * @param supplier A {@link Supplier} through which the value is obtained
   */
  public LazyValue(Supplier<T> supplier) {
    checkArgument(supplier != null, "supplier cannot be null");
    valueSupplier = supplier;
  }

  /**
   * Creates a new instance which is already initialised with the given {@code value}.
   *
   * @param value the initialization value
   */
  public LazyValue(T value) {
    this.value = value;
    this.initialized = true;
    valueSupplier = () -> value;
  }

  /**
   * Returns the lazy value. If the value has not yet been computed, then it does so
   *
   * @return the lazy value
   */
  @Override
  public T get() {
    if (!initialized) {
      synchronized (this) {
        if (!initialized) {
          this.value = valueSupplier.get();
          // This is needed so the GC may collect all objects referenced by this supplier, eventually.
          this.valueSupplier = null;
          this.initialized = true;
        }
      }
    }

    return value;
  }

  /**
   * @return Whether the value has already been calculated.
   */
  public boolean isComputed() {
    return initialized;
  }

  /**
   * If the value has already been computed, if passes it to the given {@code consumer}.
   *
   * This method does not perform any synchronization so keep in mind that dirty reads are possible if this method is being called
   * from one thread while another thread is triggering the value's computation
   *
   * @param consumer a {@link Consumer}
   */
  public void ifComputed(Consumer<T> consumer) {
    if (initialized) {
      consumer.accept(value);
    }
  }

  /**
   * Applies the given {@code function} through the output of {@link #get()}.
   *
   * If the value has not already been computed, this method will trigger computation. This method is thread-safe.
   *
   * @param function a transformation function
   * @param <R>      the generic type of the function's output
   * @return a transformed value
   */
  public <R> R flatMap(Function<T, R> function) {
    return function.apply(get());
  }
}
