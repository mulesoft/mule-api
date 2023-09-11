/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link LazyValue} that adds logic for serialization. In order to work properly, values must implement the
 * {@link Serializable} interface.
 * </p>
 * While serializing, if the value was not yet resolved, it will be. As a consequence, {@link LazyValue#isComputed()} will return
 * different values before and after serialization.
 *
 * @since 1.2.0
 */
public class SerializableLazyValue<T extends Serializable> extends LazyValue<T> implements Serializable {

  private static final long serialVersionUID = 8803029647811486550L;

  public SerializableLazyValue(Supplier<T> supplier) {
    super(supplier);
  }

  public SerializableLazyValue(T value) {
    super(value);
  }

  @Override
  public T get() {
    return super.get();
  }

  @Override
  public <R> R flatMap(Function<T, R> function) {
    return super.flatMap(function);
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
    initialized = in.readBoolean();
    value = (T) in.readObject();
    valueSupplier = () -> value;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    if (!initialized) {
      get();
    }
    out.writeBoolean(initialized);
    out.writeObject(value);
  }
}
