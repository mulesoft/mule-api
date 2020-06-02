/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.exception;

import static java.util.Objects.requireNonNull;
import static org.mule.runtime.api.exception.ExceptionHelper.getExceptionReader;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.Error;

import java.util.Objects;

/**
 * Wraps a provided exception, or suppressing a {@link MuleException} that is part of it's cause tree, meaning that the Mule Runtime will not take it into account for the error handling.
 * The suppressed cause and all its nested {@link Exception#getCause()} will not be taken into account during the {@link org.mule.runtime.api.message.Error} resolution.
 * <br/><br/>Example without suppression:
 * <br/><pre>throw new {@link org.mule.runtime.api.exception.TypedException}(new {@link org.mule.runtime.api.connection.ConnectionException}(), {@link org.mule.runtime.api.message.ErrorType customErrorType})</pre>
 * will resolve to an {@link Error} whith {@link Error#getErrorType()} returning ConnectionException's error type (discarding the top level customErrorType)
 * <br/><br/>Same example with suppression:
 * <br/><pre>throw new {@link org.mule.runtime.api.exception.TypedException}(new {@link SuppressedMuleException}(new {@link org.mule.runtime.api.connection.ConnectionException}()), {@link org.mule.runtime.api.message.ErrorType customErrorType})</pre>
 * will resolve to an {@link Error} with {@link Error#getErrorType()} returning customErrorType (discarding the underlying ConnectionException's error type})
 * @since 1.2.3, 1.3
 */
public class SuppressedMuleException extends MuleException {

  private static final long serialVersionUID = -2020531237382360468L;

  private final MuleException suppressedException;

  /**
   * Constructs a new {@link SuppressedMuleException}
   * @param throwable Exception that will be wrapped
   * @param causeToSuppress The cause that wants to be suppressed. Cannot be null.
   */
  private SuppressedMuleException(Throwable throwable, MuleException causeToSuppress) {
    super(requireNonNull(throwable, "Exception cannot be null"));
    this.suppressedException = requireNonNull(causeToSuppress, "Cannot suppress a null cause");
    getExceptionInfo().setCausedBy(causeToSuppress.getMessage());
  }

  /**
   * @return {@link MuleException} that has been suppressed by this {@link SuppressedMuleException}
   */
  public MuleException getSuppressedMuleException() {
    return suppressedException;
  }

  /**
   * Wraps the provided exception, suppressing the exception itself or the first cause that is an instance of the provided class.
   * The search will stop if a {@link SuppressedMuleException} is found.
   * @param exception Exception that will be wrapped, suppressing the exception itself or one of it's causes.
   * @param causeToSuppress Class of the {@link MuleException} that has to be suppressed.
   * @return {@link SuppressedMuleException} or provided exception if no cause to suppress is found.
   */
  public static Throwable suppressIfPresent(Throwable exception, Class<? extends MuleException> causeToSuppress) {
    Throwable cause = exception;
    while (cause != null && !(cause instanceof SuppressedMuleException)) {
      if (causeToSuppress.isInstance(cause)) {
        return new SuppressedMuleException(exception, (MuleException) cause);
      }
      cause = cause.getCause();
      // Address some misbehaving exceptions, avoid endless loop
      if (exception == cause) {
        break;
      }
    }
    return exception;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    SuppressedMuleException that = (SuppressedMuleException) o;
    return suppressedException.equals(that.suppressedException);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), suppressedException);
  }
}
