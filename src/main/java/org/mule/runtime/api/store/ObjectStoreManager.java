/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Creates and manages {@link ObjectStore} instances.
 * <p>
 * Any component in need to use an {@link ObjectStore} should created it through an implementation of this interface.
 * <p>
 * All {@link ObjectStore} instances created through an instance of this interface, should also be destroyed through the
 * same instance.
 * <p>
 * Implementations are required to be thread-safe.
 *
 * @since 1.0
 */
public interface ObjectStoreManager {


  /**
   * Returns an {@link ObjectStore} previously defined through the {@link #createObjectStore(String, ObjectStoreSettings)}
   * method.
   * <p>
   * If the {@code name} doesn't match with a store previously created through that method, or if the matching
   * store was disposed through {@link #disposeStore(String)}, then this method will throw {@link NoSuchElementException}.
   * <p>
   * Otherwise, invoking this method several times using equivalent names will always result in <b>the same</b> instance
   * being returned.
   *
   * @param name the name of the object store
   * @param <T>  the generic type of the items in the store
   * @return an {@link ObjectStore}
   * @throws NoSuchElementException if the store doesn't exist or has been disposed
   */
  <T extends ObjectStore<? extends Serializable>> T getObjectStore(String name);

  /**
   * Creates and returns a new {@link ObjectStore}, configured according to the state of the {@code settings} object.
   * <p>
   * If is {@link #getObjectStore(String)} after this method with an equivalent {@code name}, it will return the same
   * instance as this method.
   * <p>
   * If this method is invoked with a {@code name} for which an ObjectStore has already been created, it will throw
   * {@link IllegalArgumentException}, if the {@code settings} of the two objects differ.
   *
   * @param name     the name of the object store
   * @param settings the object store configuration
   * @param <T>      the generic type of the items in the store
   * @return a new {@link ObjectStore}
   * @throws IllegalArgumentException if the store already exists
   */
  <T extends ObjectStore<? extends Serializable>> T createObjectStore(String name, ObjectStoreSettings settings);

  /**
   * Clears the object store of the given {@code name} and releases all resources associated to it, including memory,
   * storage, etc.
   * <p>
   * The referenced store needs to have been created through the {@link #createObjectStore(String, ObjectStoreSettings)} method
   * or a {@link NoSuchElementException} will be thrown.
   *
   * @param name the name of the {@link ObjectStore} to be disposed.
   */
  void disposeStore(String name) throws ObjectStoreException;
}
