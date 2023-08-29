/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.store;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * This exception is thrown when an {@link ObjectStore} tries to store a value using a key for which another value is already
 * present.
 *
 * @since 1.0
 */
public final class ObjectAlreadyExistsException extends ObjectStoreException {

  public ObjectAlreadyExistsException() {
    super();
  }

  public ObjectAlreadyExistsException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public ObjectAlreadyExistsException(I18nMessage message) {
    super(message);
  }

  public ObjectAlreadyExistsException(Throwable cause) {
    super(cause);
  }
}


