/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.message.Error;
import org.mule.runtime.api.message.ErrorType;

/**
 * Wraps a provided exception as suppressed, meaning that the Mule Runtime will not take it into account for the error handling.
 * The provided cause and all its nested {@link Exception#getCause()} will not be taken into account during the
 * {@link org.mule.runtime.api.message.Error} resolution.<br/>
 * Example:<br/>
 * <pre>throw new {@link org.mule.soap.api.transport.DispatcherException}(new {@link org.mule.runtime.api.connection.ConnectionException}())</pre><br/>
 * will resolve to a {@link Error#getErrorType()} with {@link ErrorType#getIdentifier()} returning 'CONNECTIVITY'<br/>
 * <pre>throw new {@link org.mule.soap.api.transport.DispatcherException}(new {@link SuppressedMuleException}(new {@link org.mule.runtime.api.connection.ConnectionException}(...)))</pre><br/>
 * will resolve to a {@link Error#getErrorType()} with {@link ErrorType#getIdentifier()} returning 'CANNOT_DISPATCH'
 * @since 4.3
 */
public class SuppressedMuleException extends MuleException {

  private static final long serialVersionUID = -2020531237382360468L;

  /**
   * Constructs a new {@link SuppressedMuleException}
   * @param causeToSuppress The cause that wants to be suppressed. Cannot be null.
   */
  public SuppressedMuleException(Exception causeToSuppress) {
    super(causeToSuppress);
    if (causeToSuppress == null) {
      throw new MuleRuntimeException(new IllegalArgumentException("Cannot suppress a null cause"));
    }
  }

}
