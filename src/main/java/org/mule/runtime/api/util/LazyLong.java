/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.util;

import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * Provides a native long which may be lazily computed.
 * <p>
 * The value is only computed on the first invokation of {@link #getAsLong()} ()}. Subsequent calls to such method will always
 * return the same value.
 * <p>
 * This class is thread-safe. When invoking {@link #getAsLong()} ()}, it is guaranteed that the value will be computed only once.
 *
 * @since 1.1.3
 */
public class LazyLong implements LongSupplier {

  private volatile boolean initialized = false;
  private long value;
  private LongSupplier valueSupplier;

  /**
   * Creates a new instance which lazily obtains its value from the given {@code supplier}. It is guaranteed that
   * {@link Supplier#get()} will only be invoked once. Because this class is thread-safe, the supplier is not required to be.
   *
   * @param supplier A {@link Supplier} through which the value is obtained
   */
  public LazyLong(LongSupplier supplier) {
    checkArgument(supplier != null, "supplier cannot be null");
    valueSupplier = supplier;
  }

  /**
   * Creates a new instance which is already initialised with the given {@code value}.
   *
   * @param value the initialization value
   */
  public LazyLong(long value) {
    this.value = value;
    this.initialized = true;
  }

  /**
   * Returns the lazy value. If the value has not yet been computed, then it does so
   *
   * @return the lazy value
   */
  @Override
  public long getAsLong() {
    if (!initialized) {
      synchronized (this) {
        if (!initialized) {
          this.value = valueSupplier.getAsLong();
          // This is needed so the GC may collect all objects referenced by this supplier, eventually.
          this.valueSupplier = () -> value;
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
  public void ifComputed(LongConsumer consumer) {
    if (initialized) {
      consumer.accept(value);
    }
  }

  /**
   * Applies the given {@code function} through the output of {@link #getAsLong()} ()}.
   *
   * If the value has not already been computed, this method will trigger computation. This method is thread-safe.
   *
   * @param function a transformation function
   * @param <R>      the generic type of the function's output
   * @return a transformed value
   */
  public <R> R flatMap(Function<Long, R> function) {
    return function.apply(getAsLong());
  }
}
