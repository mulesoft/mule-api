/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
