/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * This exception class is thrown in cases when an exception occurs while operating on an {@link ObjectStore}.
 *
 * @since 1.0
 */
public class ObjectStoreException extends MuleException {

  public ObjectStoreException() {
    super();
  }

  public ObjectStoreException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public ObjectStoreException(I18nMessage message) {
    super(message);
  }

  public ObjectStoreException(Throwable cause) {
    super(cause);
  }
}
