/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.connection;

import static java.util.Optional.ofNullable;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.message.ErrorType;

import java.util.Optional;

/**
 * Represents the result of a Connection Validation.
 *
 * @since 1.0
 */
@NoExtend
public class ConnectionValidationResult {

  private boolean validationStatus;
  private String message;
  private ErrorType errorType;
  private Exception exception;

  /**
   * For internal use only. This class is not part of the API. Do not use.
   */
  @NoInstantiate
  protected ConnectionValidationResult() {}

  private ConnectionValidationResult(boolean validationStatus, String message, Exception exception) {
    this(validationStatus, message, null, exception);
  }

  private ConnectionValidationResult(boolean validationStatus, String message, ErrorType errorType, Exception exception) {
    this.validationStatus = validationStatus;
    this.message = message;
    this.errorType = errorType;
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
   * @param exception The exception that causes the connection invalidity
   * @return a {@link ConnectionValidationResult} with a invalid status.
   */
  public static ConnectionValidationResult failure(String message, Exception exception) {
    return new ConnectionValidationResult(false, message, exception);
  }

  /**
   * @param message   Message in case of a invalid connection
   * @param errorType An {@link ErrorType} that represents the cause of the invalid connection
   * @param exception The exception that causes the connection invalidity
   * @return a {@link ConnectionValidationResult} with a invalid status.
   */
  public static ConnectionValidationResult failure(String message, ErrorType errorType, Exception exception) {
    return new ConnectionValidationResult(false, message, errorType, exception);
  }

  /**
   * @return A {@link boolean} indicating if the connection is valid or not.
   */
  public boolean isValid() {
    return this.validationStatus;
  }

  /**
   * @return A {@link String} indicating the Validation message. The message should not be null in case of a invalid connection.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * @return A {@link ErrorType} indicating the type of the occurred problem.
   */
  public Optional<ErrorType> getErrorType() {
    return ofNullable(errorType);
  }

  /**
   * @return The {@link Exception} that causes the connection invalidity. The exception should not be null in case of a invalid
   *         connection.
   */
  public Exception getException() {
    return this.exception;
  }
}
