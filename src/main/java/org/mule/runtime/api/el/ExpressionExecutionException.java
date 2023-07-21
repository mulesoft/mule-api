/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
