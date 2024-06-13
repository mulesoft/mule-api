/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import static java.util.Collections.unmodifiableMap;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleMemoryObjectStore<T extends Serializable> extends TemplateObjectStore<T> implements ObjectStore<T> {

  private Map<String, T> map = new ConcurrentHashMap<>();

  @Override
  public boolean isPersistent() {
    return false;
  }

  @Override
  protected boolean doContains(String key) throws ObjectStoreException {
    return map.containsKey(key);
  }

  @Override
  protected void doStore(String key, T value) throws ObjectStoreException {
    if (value == null) {
      throw new ObjectStoreException(createStaticMessage("The required object/property \"value\" is null"));
    }
    map.put(key, value);
  }

  @Override
  protected T doRetrieve(String key) throws ObjectStoreException {
    return map.get(key);
  }

  @Override
  public void clear() throws ObjectStoreException {
    this.map.clear();
  }

  @Override
  protected T doRemove(String key) {
    return map.remove(key);
  }

  @Override
  public Map<String, T> retrieveAll() throws ObjectStoreException {
    return unmodifiableMap(map);
  }

  @Override
  public void open() throws ObjectStoreException {
    // this is a no-op
  }

  @Override
  public void close() throws ObjectStoreException {
    // this is a no-op
  }

  @Override
  public List<String> allKeys() throws ObjectStoreException {
    return new ArrayList<>(map.keySet());
  }
}
