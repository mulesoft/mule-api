/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import static org.mule.runtime.api.store.ObjectStoreSettings.unmanagedPersistent;
import org.mule.runtime.api.config.custom.CustomizationService;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * Creates and manages {@link ObjectStore} instances.
 * <p>
 * Any component in need to use an {@link ObjectStore} should created it through an implementation of this interface.
 * <p>
 * This manager works under the concept of &quot;Base Object Stores&quot;. This means that all created Object Stores are actually
 * partitions on these base stores. This manager is in charge of creating and handling such partitions and exposing them as stand
 * alone stores. There're two base object stores, one for the in memory ones and another for the transient ones.
 * <p>
 * The reason why this manager operates in this way is to allow other services to override the default object store
 * implementations. A Core Extension can use the {@link CustomizationService} to override those base stores using the
 * {@link #BASE_IN_MEMORY_OBJECT_STORE_KEY} and {@link #BASE_PERSISTENT_OBJECT_STORE_KEY} keys. In addition to that, this manager
 * also has the concept of a default partition, which is the one for general use, for cases in which there's no actual need to
 * define a particular store. The default partition is unbounded and persistent.
 * <p>
 * All {@link ObjectStore} instances created through an instance of this interface, should also be destroyed through the same
 * instance using the {@link #disposeStore(String)} method. This does not mean that all stores should necessarily be disposed. If
 * you want that store to endure through time, then you should not dispose it.
 * <p>
 * Implementations are required to be thread-safe.
 *
 * @since 1.0
 */
public interface ObjectStoreManager {

  /**
   * The key of the base in memory object store.
   */
  String BASE_IN_MEMORY_OBJECT_STORE_KEY = "_defaultInMemoryObjectStore";

  /**
   * The key of the base persistent object store.
   */
  String BASE_PERSISTENT_OBJECT_STORE_KEY = "_defaultPersistentObjectStore";

  /**
   * The name of the default partition
   */
  String DEFAULT_PARTITION_NAME = "_defaultPartition";

  /**
   * Returns an {@link ObjectStore} previously defined through the {@link #createObjectStore(String, ObjectStoreSettings)} method.
   * <p>
   * If the {@code name} doesn't match with a store previously created through that method, or if the matching store was disposed
   * through {@link #disposeStore(String)}, then this method will throw {@link NoSuchElementException}.
   * <p>
   * The returned store has to be already open. Invokers should not have to call {@link ObjectStore#open()} on the returned
   * instance.
   * <p>
   * Otherwise, invoking this method several times using equivalent names will always result in <b>the same</b> instance being
   * returned.
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
   * If is {@link #getObjectStore(String)} after this method with an equivalent {@code name}, it will return the same instance as
   * this method.
   * <p>
   * The returned store has to be already open. Invokers should not have to call {@link ObjectStore#open()} on the returned
   * instance.
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
   * Returns the {@link ObjectStore} of {@code name} if it has already been defined. Otherwise, it delegates into
   * {@link #createObjectStore(String, ObjectStoreSettings)} to create it.
   * <p>
   * Notice that if the store does actually exist, then the returned store might not actually match the provided {@code settings}
   *
   * @param name     the name of the object store
   * @param settings the object store configuration
   * @param <T>      the generic type of the items in the store
   * @return an {@link ObjectStore}
   */
  <T extends ObjectStore<? extends Serializable>> T getOrCreateObjectStore(String name, ObjectStoreSettings settings);

  /**
   * Returns the default partition, which is unbounded and persistent
   * 
   * @param <T> the generic type of the items in the store
   * @return the default partition {@link ObjectStore}
   */
  default <T extends ObjectStore<? extends Serializable>> T getDefaultPartition() {
    return getOrCreateObjectStore(DEFAULT_PARTITION_NAME, unmanagedPersistent());
  }

  /**
   * Clears the object store of the given {@code name} and releases all resources associated to it, including memory, storage,
   * etc.
   * <p>
   * The referenced store needs to have been created through the {@link #createObjectStore(String, ObjectStoreSettings)} method or
   * a {@link NoSuchElementException} will be thrown.
   *
   * @param name the name of the {@link ObjectStore} to be disposed.
   */
  void disposeStore(String name) throws ObjectStoreException;
}
