/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * This exception is thrown when an {@link ObjectStore} attempts to operate on a key for which it doesn't have an associated value
 *
 * @since 1.0
 */
public final class ObjectDoesNotExistException extends ObjectStoreException {

  public ObjectDoesNotExistException() {
    super();
  }

  public ObjectDoesNotExistException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public ObjectDoesNotExistException(I18nMessage message) {
    super(message);
  }

  public ObjectDoesNotExistException(Throwable cause) {
    super(cause);
  }
}


