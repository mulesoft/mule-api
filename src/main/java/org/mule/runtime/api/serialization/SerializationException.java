/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.serialization;

import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.i18n.I18nMessageFactory;

/**
 * Exception to signal an error during serialization/deserialization process
 *
 * @since 1.0
 */
public final class SerializationException extends MuleRuntimeException {

  private static final long serialVersionUID = -2550225226351711742L;

  public SerializationException(String message, Throwable cause) {
    this(I18nMessageFactory.createStaticMessage(message), cause);
  }

  public SerializationException(String message) {
    this(I18nMessageFactory.createStaticMessage(message));
  }

  public SerializationException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public SerializationException(I18nMessage message) {
    super(message);
  }

}
