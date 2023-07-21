/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import java.io.Serializable;

/**
 * An {@link ObjectStore} capable of expiring entries when the {@link #expire(long, int)} method is invoked
 *
 * @param <T> the generic type of the objects to be stored
 * @since 1.0
 */
public interface ExpirableObjectStore<T extends Serializable> extends ObjectStore<T> {

  /**
   * Expires eligible entries. This method is required to be thread safe and atomic, meaning that while running, all other methods
   * must wait for it to finish
   *
   * @param entryTTL   expire all entries which were inserted after this number of milliseconds. If lower or equal than zero, no
   *                   items will be expired on a TTL basis
   * @param maxEntries The max number of entries that this store is allowed to have. If the store has more entries than this, it
   *                   will start removing entries until the boundary is met. The selection criteria is up to each implementation
   * @throws ObjectStoreException in case of failure
   */
  void expire(long entryTTL, int maxEntries) throws ObjectStoreException;
}
