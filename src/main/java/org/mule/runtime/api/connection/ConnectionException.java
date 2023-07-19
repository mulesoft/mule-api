/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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

  private Object connection;
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
   * @param message   the detail message
   * @param cause     the exception's cause
   * @param errorType the exception's errorType
   */
  public ConnectionException(String message, Throwable cause, ErrorType errorType) {
    super(createStaticMessage(message), cause);
    this.errorType = errorType;
  }

  /**
   * Creates a new instance with the specified detail {@code cause} and {@code failed connection}
   *
   * @param cause      the exception's cause
   * @param connection the failed connection
   */
  public ConnectionException(Throwable cause, Object connection) {
    super(cause);
    this.connection = connection;
  }

  /**
   * Creates a new instance with the specified detail {@code message}, {@code cause}, {@link ErrorType} and
   * {@code failed connection}
   *
   * @param message    the detail message
   * @param cause      the exception's cause
   * @param errorType  the exception's errorType
   * @param connection the failed connection
   */
  public ConnectionException(String message, Throwable cause, ErrorType errorType, Object connection) {
    super(createStaticMessage(message), cause);
    this.errorType = errorType;
    this.connection = connection;
  }

  /**
   * @return The {@link Optional} {@link ErrorType} for this exception
   */
  public Optional<ErrorType> getErrorType() {
    return ofNullable(errorType);
  }

  /**
   * @return The {@link Optional} connection. This connection is the one that failed.
   */
  public Optional<Object> getConnection() {
    return ofNullable(connection);
  }
}
