/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.map.ObjectStoreEntryListener;

import java.io.Serializable;

/**
 * Template for {@link ObjectStore} implementations so that it's easier to conform to the contract.
 * <p>
 * {@link ObjectStore} implementations are not required to extend this class, but it is recommended to do so in order to help
 * guaranteeing that implementations implement the contract correctly.
 *
 * @since 1.0
 */
public abstract class TemplateObjectStore<T extends Serializable> extends AbstractObjectStoreSupport<T> {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(String key) throws ObjectStoreException {
    validateKey(key);
    return doContains(key);
  }

  /**
   * Template method for {@link #contains(String)}. Implement this method assuming that the {@code key} is already valid
   * {@inheritDoc}
   */
  protected abstract boolean doContains(String key) throws ObjectStoreException;

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(String key, T value) throws ObjectStoreException {
    validateKey(key);

    if (contains(key)) {
      throw new ObjectAlreadyExistsException(createStaticMessage("ObjectStore already contains entry for key " + key));
    }
    doStore(key, value);
  }

  /**
   * Template method for {@link #store(String, Serializable)}. Implement this method assuming that the key is valid and that
   * {@link #contains(String)} has already been invoked to verify that the key doesn't already exists in this store. {@inheritDoc}
   */
  protected abstract void doStore(String key, T value) throws ObjectStoreException;

  /**
   * {@inheritDoc}
   */
  @Override
  public T retrieve(String key) throws ObjectStoreException {
    validatePresentKey(key);
    return doRetrieve(key);
  }

  /**
   * Template method for {@link #store(String, Serializable)}. Implement this method assuming that the key is valid and that
   * {@link #contains(String)} has already been invoked to verify that the key actually exists in this store. {@inheritDoc}
   */
  protected abstract T doRetrieve(String key) throws ObjectStoreException;

  /**
   * {@inheritDoc}
   */
  @Override
  public T remove(String key) throws ObjectStoreException {
    validatePresentKey(key);
    T value = doRemove(key);
    if (value == null) {
      throw new ObjectDoesNotExistException(createStaticMessage("Object store does not contain a value for key " + key));
    }

    return value;
  }

  /**
   * Template method for {@link #remove(String)} (String, Serializable)}. Implement this method assuming that the key is valid and
   * that {@link #contains(String)} has already been invoked to verify that the key actually exists in this store. {@inheritDoc}
   */
  protected abstract T doRemove(String key) throws ObjectStoreException;
}
