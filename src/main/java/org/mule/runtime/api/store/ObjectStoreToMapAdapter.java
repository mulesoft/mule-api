/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.exception.MuleRuntimeException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Adapts the object store interface to a map interface so the client doesn't have to deal with all the ObjectStoreExceptions
 * thrown by ObjectStore.
 * <p>
 * This class provides limited functionality from the Map interface. It does not support some methods (see methods javadoc) that
 * can have a big impact in performance due the underlying object store being used.
 * <p>
 * The object store provided will be access for completing the map operations but the whole lifecycle of the provided object store
 * must be handled by the user.
 * <p>
 * Operations of this map are not thread safe so the user must synchronize access to this map properly.
 * <p>
 * If a method detects a data hazard such that the underlying object store throws an exception, it returns null instead of
 * propagate the exception, in order to honor the Map interface.
 *
 * @param <T> the generic type of the instances contained in the {@link ObjectStore}
 *
 * @since 1.0
 */
public abstract class ObjectStoreToMapAdapter<T extends Serializable> implements Map<String, T> {

  protected abstract ObjectStore<T> getObjectStore();

  @Override
  public int size() {
    try {
      return getObjectStore().allKeys().size();
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public boolean isEmpty() {
    try {
      return getObjectStore().allKeys().isEmpty();
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public boolean containsKey(Object key) {
    try {
      return getObjectStore().contains((String) key);
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public boolean containsValue(Object value) {
    throw new UnsupportedOperationException("Map adapter for object store does not support contains value");
  }

  @Override
  public T get(Object key) {
    try {
      if (!getObjectStore().contains((String) key)) {
        return null;
      }
      return getObjectStore().retrieve((String) key);
    } catch (ObjectDoesNotExistException e) {
      // Object could be removed concurrently.
      return null;
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public T put(String key, T value) {
    T previousValue = null;
    try {
      if (getObjectStore().contains(key)) {
        previousValue = getObjectStore().retrieve(key);
        getObjectStore().remove(key);
      }
      if (value != null) {
        getObjectStore().store(key, value);
      }
      return previousValue;
    } catch (ObjectDoesNotExistException | ObjectAlreadyExistsException e) {
      // We should ignore these exceptions here, because an object could be:
      // * Removed concurrently between contains and retrieve.
      // * Removed between contains and remove.
      // * Added between contains and store.
      return null;
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public T remove(Object key) {
    try {
      if (getObjectStore().contains((String) key)) {
        return getObjectStore().remove((String) key);
      }
    } catch (ObjectDoesNotExistException e) {
      // Object could be removed concurrently.
      return null;
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
    return null;
  }

  @Override
  public void putAll(Map<? extends String, ? extends T> mapToAdd) {
    for (String key : mapToAdd.keySet()) {
      put(key, mapToAdd.get(key));
    }
  }

  @Override
  public void clear() {
    try {
      getObjectStore().clear();
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  @Override
  public Set<String> keySet() {
    try {
      final List<String> allKeys = getObjectStore().allKeys();
      return new HashSet(allKeys);
    } catch (ObjectStoreException e) {
      throw new MuleRuntimeException(e);
    }
  }

  /**
   * This method is not supported for performance reasons
   */
  @Override
  public Collection<T> values() {
    throw new UnsupportedOperationException("ObjectStoreToMapAdapter does not support values() method");
  }

  /**
   * This method is not supported for performance reasons
   */
  @Override
  public Set<Entry<String, T>> entrySet() {
    throw new UnsupportedOperationException("ObjectStoreToMapAdapter does not support entrySet() method");
  }
}
