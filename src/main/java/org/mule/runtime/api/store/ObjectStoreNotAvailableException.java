/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * This exception is thrown when the underlying to an {@link ObjectStore}'s system fails.
 *
 * @since 1.0
 */
public final class ObjectStoreNotAvailableException extends ObjectStoreException {

  public ObjectStoreNotAvailableException() {
    super();
  }

  public ObjectStoreNotAvailableException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public ObjectStoreNotAvailableException(I18nMessage message) {
    super(message);
  }

  public ObjectStoreNotAvailableException(Throwable cause) {
    super(cause);
  }
}


