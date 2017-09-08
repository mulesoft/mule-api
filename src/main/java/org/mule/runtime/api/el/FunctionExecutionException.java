/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Indicates than an error occurred while executing a function.
 *
 * @since 1.0
 */
public class FunctionExecutionException extends MuleRuntimeException {

  public FunctionExecutionException(I18nMessage message) {
    super(message);
  }

  public FunctionExecutionException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public FunctionExecutionException(Throwable cause) {
    super(cause);
  }

}
