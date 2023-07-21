/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.api.annotation.NoExtend;

import java.io.Serializable;

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
}
