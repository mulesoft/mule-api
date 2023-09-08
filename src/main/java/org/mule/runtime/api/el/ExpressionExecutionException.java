/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Indicates than an error occurred while executing an expression.
 *
 * @since 1.0
 */
public final class ExpressionExecutionException extends MuleRuntimeException {

  public ExpressionExecutionException(I18nMessage message) {
    super(message);
  }

  public ExpressionExecutionException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public ExpressionExecutionException(Throwable cause) {
    super(cause);
  }
}
