/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

/**
 * Represents the result of a Connection Validation.
 *
 * @since 1.0
 */
public class ConnectionValidationResult {

  private boolean validationStatus;
  private String message;
  private ConnectionExceptionCode code;
  private String exception;

  private ConnectionValidationResult(boolean validationStatus, String message, ConnectionExceptionCode code,
                                     String exception) {

    this.validationStatus = validationStatus;
    this.message = message;
    this.code = code;
    this.exception = exception;
  }

  /**
   * @return a {@link ConnectionValidationResult} with a valid status.
   */
  public static ConnectionValidationResult success() {
    return new ConnectionValidationResult(true, null, null, null);
  }

  /**
   * @param message   Message in case of a invalid connection
   * @param code      A {@link ConnectionExceptionCode} that represents the cause of the invalid connection
   * @param exception The exception that causes the connection invalidity
   * @return a {@link ConnectionValidationResult} with a invalid status.
   */
  public static ConnectionValidationResult failure(String message, ConnectionExceptionCode code, Exception exception) {
    return new ConnectionValidationResult(false, message, code, getStackTrace(exception));
  }

  /**
   * @param message   Message in case of a invalid connection
   * @param code      A {@link ConnectionExceptionCode} that represents the cause of the invalid connection
   * @param exception The exception stack trace that causes the connection invalidity
   * @return a {@link ConnectionValidationResult} with a invalid status.
   */
  public static ConnectionValidationResult failure(String message, ConnectionExceptionCode code, String exception) {
    return new ConnectionValidationResult(false, message, code, exception);
  }

  /**
   * @return A {@link boolean} indicating if the connection is valid or not.
   */
  public boolean isValid() {
    return this.validationStatus;
  }

  /**
   * @return A {@link String} indicating the Validation message.
   * The message should not be null in case of a invalid connection.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * @return A {@link ConnectionExceptionCode} indicating the Validation code.
   * The code should not be null in case of a invalid connection.
   */
  public ConnectionExceptionCode getCode() {
    return this.code;
  }

  /**
   * @return The stack trace of the exception that causes the connection invalidity.
   * The stack trace should not be null in case of a invalid connection.
   */
  public String getException() {
    return this.exception;
  }
}
