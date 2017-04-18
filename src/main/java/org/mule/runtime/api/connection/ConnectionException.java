/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.ErrorType;

import java.util.Optional;

/**
 * Signals that an error occurred while trying to establish a connection
 *
 * @since 1.0
 */
public class ConnectionException extends MuleException {

  private ErrorType errorType;

  /**
   * Creates a new instance with the specified detail {@code message}
   *
   * @param message the detail message
   */
  public ConnectionException(String message) {
    super(createStaticMessage(message));
  }

  /**
   * Creates a new instance with the specified {@code cause}
   *
   * @param cause the exception's cause
   */
  public ConnectionException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new instance with the specified detail {@code message} and {@code cause}
   *
   * @param message the detail message
   * @param cause   the exception's cause
   */
  public ConnectionException(String message, Throwable cause) {
    super(createStaticMessage(message), cause);
  }

  /**
   * Creates a new instance with the specified detail {@code message}, {@code cause} and {@link ErrorType}
   *
   * @param message the detail message
   * @param cause   the exception's cause
   * @param errorType the exception's errorType
   */
  public ConnectionException(String message, Throwable cause, ErrorType errorType) {
    super(createStaticMessage(message), cause);
    this.errorType = errorType;
  }

  /**
   * @return The {@link Optional} {@link ErrorType} for this exception
   */
  public Optional<ErrorType> getErrorType() {
    return ofNullable(errorType);
  }
}
