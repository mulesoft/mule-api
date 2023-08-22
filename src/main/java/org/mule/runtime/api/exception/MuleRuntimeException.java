/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import static org.mule.runtime.api.exception.MuleException.isVerboseExceptions;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@code MuleRuntimeException} Is the base runtime exception type for the Mule Server any other runtimes exceptions thrown by
 * Mule code will use or be based on this exception. Runtime exceptions in mule are only ever thrown where the method is not
 * declared to throw an exception and the exception is serious.
 *
 * @since 1.0
 */
public class MuleRuntimeException extends RuntimeException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 6728041560892553159L;

  /**
   * @param message the exception message
   */
  public MuleRuntimeException(I18nMessage message) {
    this(message, null);
  }

  /**
   * @param message the exception message
   * @param cause   the exception that triggered this exception
   */
  public MuleRuntimeException(I18nMessage message, Throwable cause) {
    this(message != null ? message.getMessage() : null, cause);
  }

  /**
   * @param cause the exception that triggered this exception
   */
  public MuleRuntimeException(Throwable cause) {
    this(cause.toString(), cause);
  }

  private MuleRuntimeException(String message, Throwable cause) {
    super(message, cause, true, isVerboseExceptions());
  }

}
