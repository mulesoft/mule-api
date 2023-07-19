/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.exception;

import static java.util.Objects.requireNonNull;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.ErrorType;

/**
 * Internal {@link RuntimeException} implementation to throw {@link Throwable throwables} that indicates explicitly the
 * {@link ErrorType} that is wanted to throw. Also gives the possibility to declare a message for the {@link Error} being built.
 *
 * @since 1.0
 */
public class TypedException extends MuleRuntimeException {

  private static final long serialVersionUID = 5716341342349590954L;

  private final ErrorType errorType;

  /**
   * @param throwable The {@link TypedException#getCause()} of this new exception.
   * @param errorType The {@link ErrorType} that identifies the {@link TypedException#getCause()} {@link Throwable}
   */
  public TypedException(Throwable throwable, ErrorType errorType) {
    super(throwable);
    this.errorType = requireNonNull(errorType, "The 'errorType' argument can not be null");
  }

  /**
   * @param throwable The {@link TypedException#getCause()} of this new exception.
   * @param errorType The {@link ErrorType} that identifies the {@link TypedException#getCause()} {@link Throwable}
   * @param message   error message to override the once from the original exception
   */
  public TypedException(Throwable throwable, ErrorType errorType, String message) {
    super(createStaticMessage(message), throwable);
    this.errorType = requireNonNull(errorType, "The 'errorType' argument can not be null");
  }

  /**
   * @return The {@link ErrorType} of the thrown exception
   */
  public ErrorType getErrorType() {
    return errorType;
  }
}
