/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

/**
 * Signals that an error occurred while trying to establish a connection
 *
 * @since 1.0
 */
public class ConnectionException extends Exception {

  /**
   * Creates a new instance with the specified detail {@code message}
   *
   * @param message the detail message
   */
  public ConnectionException(String message) {
    super(message);
  }

  /**
   * Creates a new instance with the specified detail {@code message} and {@code cause}
   *
   * @param message the detail message
   * @param cause   the exception's cause
   */
  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance with the specified {@code cause}
   *
   * @param cause the exception's cause
   */
  public ConnectionException(Throwable cause) {
    super(cause);
  }
}
