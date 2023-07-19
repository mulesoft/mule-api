/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.exception;

import static java.util.Objects.requireNonNull;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.Error;

/**
 * Wraps a provided exception, suppressing a {@link MuleException} that is part of it's cause tree, meaning that the Mule Runtime
 * will not take it into account for the error handling. The suppressed cause and all its nested {@link Exception#getCause()} will
 * not be taken into account during the {@link org.mule.runtime.api.message.Error} resolution. <br/>
 * <br/>
 * Example without suppression: <br/>
 * 
 * <pre>
 * throw new {@link org.mule.runtime.api.exception.TypedException}(new {@link org.mule.runtime.api.connection.ConnectionException}(), {@link org.mule.runtime.api.message.ErrorType customErrorType})
 * </pre>
 * 
 * will resolve to an {@link Error} with {@link Error#getErrorType()} returning ConnectionException's error type (discarding the
 * top level customErrorType). <br/>
 * <br/>
 * Same example with suppression: <br/>
 * 
 * <pre>
 * throw new {@link org.mule.runtime.api.exception.TypedException}(new {@link SuppressedMuleException}(new {@link org.mule.runtime.api.connection.ConnectionException}()), {@link org.mule.runtime.api.message.ErrorType customErrorType})
 * </pre>
 * 
 * will resolve to an {@link Error} with {@link Error#getErrorType()} returning customErrorType (discarding the underlying
 * ConnectionException's error type}).
 * 
 * @since 1.2.3, 1.3
 */
public class SuppressedMuleException extends MuleException {

  private static final long serialVersionUID = -2020531237382360468L;

  private final MuleException suppressedException;

  /**
   * Constructs a new {@link SuppressedMuleException}
   * 
   * @param throwable       Exception that will be wrapped.
   * @param causeToSuppress The cause that wants to be suppressed. Cannot be null.
   */
  private SuppressedMuleException(Throwable throwable, MuleException causeToSuppress) {
    super(requireNonNull(throwable, "Exception cannot be null"));
    this.suppressedException = requireNonNull(causeToSuppress, "Cannot suppress a null cause");
    addSuppressionToMuleExceptionInfo(causeToSuppress);
  }

  /**
   * Completes the {@link org.mule.runtime.api.exception.MuleExceptionInfo} of a {@link SuppressedMuleException} by adding the
   * {@link MuleException} that is being suppressed and traversing its causes to include any other suppression found and also the
   * additional information from all the {@link MuleException}s.
   * 
   * @param causeToSuppress Exception that is being suppressed.
   */
  private void addSuppressionToMuleExceptionInfo(MuleException causeToSuppress) {
    this.getExceptionInfo().addSuppressedCause(causeToSuppress);
    this.addAllInfo(causeToSuppress.getAdditionalInfo());
    Throwable nestedCause = causeToSuppress;
    while (nestedCause.getCause() != null && nestedCause.getCause() != nestedCause) {
      nestedCause = nestedCause.getCause();
      if (nestedCause instanceof MuleException) {
        this.addAllInfo(((MuleException) nestedCause).getAdditionalInfo());
        if (nestedCause instanceof SuppressedMuleException) {
          this.getExceptionInfo().addSuppressedCause(((SuppressedMuleException) nestedCause).getSuppressedException());
        }
      }
    }
  }

  /**
   * Unwraps a {@link SuppressedMuleException}.
   * 
   * @return First cause that is not an instance of {@link SuppressedMuleException}.
   */
  public Throwable unwrap() {
    // There cannot be consecutive suppressions, so returning the cause is enough
    return getCause();
  }

  /**
   * @return {@link MuleException} that has been suppressed by this {@link SuppressedMuleException}.
   */
  public MuleException getSuppressedException() {
    return suppressedException;
  }

  /**
   * Wraps the provided exception, suppressing the exception itself or the first cause that is an instance of the provided class.
   * The search will stop if a {@link SuppressedMuleException} is found, making no suppression.
   * 
   * @param exception       Exception that will be wrapped, suppressing the exception itself or one of it's causes.
   * @param causeToSuppress Class of the {@link MuleException} that has to be suppressed.
   * @return {@link SuppressedMuleException} or provided exception if no cause to suppress is found.
   */
  public static Throwable suppressIfPresent(Throwable exception, Class<? extends MuleException> causeToSuppress) {
    Throwable cause = exception;
    while (cause != null && !(cause instanceof SuppressedMuleException)) {
      if (causeToSuppress.isInstance(cause)) {
        return new SuppressedMuleException(exception, (MuleException) cause);
      }
      if (cause.getCause() != cause) {
        cause = cause.getCause();
      } else {
        break;
      }
    }
    return exception;
  }

}
