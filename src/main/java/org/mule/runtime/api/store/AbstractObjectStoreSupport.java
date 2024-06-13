/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.api.annotation.NoExtend;
import org.mule.runtime.api.map.ObjectStoreEntryListener;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class with utility methods that {@link ObjectStore} implementations might be interested on.
 *
 * It is not mandatory for {@link ObjectStore} implementations to extend this class, but it does contain methods which are useful
 * for correctly implementing the {@link ObjectStore} contract.
 *
 * @param <T> the generic type of the store's items
 * @since 1.0
 */
@NoExtend
public abstract class AbstractObjectStoreSupport<T extends Serializable> implements ObjectStore<T> {

  private Map<String, ObjectStoreEntryListener> listenerMap = new ConcurrentHashMap<>();
  ObjectStoreEntryListener listener = new ObjectStoreEntryListener() {

    @Override
    public void entryAdded(Object key, Object value) {
      listenerMap.put((String) key, (ObjectStoreEntryListener) value);
    }

    @Override
    public void entryRemoved(Object key) {
      listenerMap.remove((String) key);
    }

    @Override
    public void entryUpdated(Object key, Object value) {
      listenerMap.put((String) key, (ObjectStoreEntryListener) value);
    }
  };

  protected void validateKey(String key) throws ObjectStoreException {
    if (key == null || key.trim().length() == 0) {
      throw new ObjectStoreException(createStaticMessage("key cannot be null or blank"));
    }
  }

  protected void validatePresentKey(String key) throws ObjectStoreException {
    validateKey(key);

    if (!contains(key)) {
      throw new ObjectDoesNotExistException(createStaticMessage("Key does not exist: " + key));
    }
  }

  @Override
  public String addEntryListener(ObjectStoreEntryListener listener) {
    String id = UUID.randomUUID().toString();
    listenerMap.put(id, listener);
    return id;
  }

  @Override
  public boolean removeEntryListener(String key) {
    if (listenerMap.remove(key) == null) {
      return false;
    }
    return true;
  }
ab

}
