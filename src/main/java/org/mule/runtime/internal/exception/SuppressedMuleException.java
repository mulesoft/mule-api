/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.exception;

import static java.util.Objects.requireNonNull;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.message.Error;

/**
 * Wraps a provided exception as suppressed, meaning that the Mule Runtime will not take it into account for the error handling.
 * The provided cause and all its nested {@link Exception#getCause()} will not be taken into account during the
 * {@link org.mule.runtime.api.message.Error} resolution.
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

  /**
   * Constructs a new {@link SuppressedMuleException}
   *
   * @param causeToSuppress The cause that wants to be suppressed. Cannot be null.
   */
  public SuppressedMuleException(Throwable causeToSuppress) {
    super(requireNonNull(causeToSuppress, "Cannot suppress a null cause"));
  }
}
