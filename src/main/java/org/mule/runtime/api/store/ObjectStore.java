/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.map.EntryListener;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A generic object key value store
 *
 * @param <T> the generic type of the objects to be stored
 * @since 1.0
 */
public interface ObjectStore<T extends Serializable> {

  /**
   * Check whether this store already contains a value for the given {@code key}
   *
   * @param key the identifier of the object to check
   * @return <code>true</code> if the key is stored or <code>false</code> no value was stored for the key.
   * @throws ObjectStoreException             if the given key is <code>null</code>.
   * @throws ObjectStoreNotAvailableException if any implementation-specific error occured, e.g. when the store is not available
   */
  boolean contains(String key) throws ObjectStoreException;

  /**
   * Store the given Object.
   *
   * @param key   the identifier for <code>value</code>
   * @param value the Object to store with <code>key</code>
   * @throws ObjectStoreException             if the given key cannot be stored or is <code>null</code>.
   * @throws ObjectStoreNotAvailableException if the store is not available or any other implementation-specific error occured.
   * @throws ObjectAlreadyExistsException     if an attempt is made to store an object for a key that already has an object
   *                                          associated.
   */
  void store(String key, T value) throws ObjectStoreException;

  /**
   * * Retrieve the object stored under the given {@code key}
   *
   * @param key the identifier of the object to retrieve.
   * @return the object associated with the given key. If no object for the given key was found this method throws an
   *         {@link ObjectDoesNotExistException}.
   * @throws ObjectStoreException             if the given key is <code>null</code>.
   * @throws ObjectStoreNotAvailableException if the store is not available or any other implementation-specific error occured.
   * @throws ObjectDoesNotExistException      if no value for the given key was previously stored.
   */
  T retrieve(String key) throws ObjectStoreException;

  /**
   * Remove the object with the given {@code key}
   *
   * @param key the identifier of the object to remove.
   * @return the object that was previously stored for the given key
   * @throws ObjectStoreException        if the given key is <code>null</code> or if the store is not available or any other
   *                                     implementation-specific error occured
   * @throws ObjectDoesNotExistException if no value for the given key was previously stored.
   */
  T remove(String key) throws ObjectStoreException;

  /**
   * @return Whether {@code this} store is persistent or not
   */
  boolean isPersistent();

  /**
   * Removes all items of this store without disposing it, meaning that after performing a clear(), you should still be able
   * perform other operations. Implementations of this method have to remove all items in the fastest way possible. No assumptions
   * should be made regarding thread safeness. If the store implementation is thread-safe, then this method should also be. If the
   * implementation does not guarantee thread-safeness, then you shouldn't expect that from this method either.
   *
   * @throws ObjectStoreException if the operation fails
   */
  void clear() throws ObjectStoreException;

  /**
   * Open the underlying store.
   *
   * @throws ObjectStoreException if an exception occurred while opening the underlying store.
   */
  void open() throws ObjectStoreException;

  /**
   * Close the underlying store.
   *
   * @throws ObjectStoreException if an exception occurred while closing the underlying store.
   */
  void close() throws ObjectStoreException;

  /**
   * @return list containing all keys that this object store currently holds values for.
   * @throws ObjectStoreException if an exception occurred while collecting the list of all keys.
   */
  List<String> allKeys() throws ObjectStoreException;

  /**
   * @return All the key-value pairs that this object store currently holds
   * @throws ObjectStoreException if an exception occurred while collecting the values
   */
  Map<String, T> retrieveAll() throws ObjectStoreException;

  UUID addEntryListener(EntryListener<String, String> listener);

  /**
   * Removes the specified entry listener.
   * <p>
   * Returns silently if there is no such listener added before.
   *
   * @param id ID of registered listener
   * @return true if registration is removed, false otherwise
   */
  boolean removeEntryListener(UUID id);
}
