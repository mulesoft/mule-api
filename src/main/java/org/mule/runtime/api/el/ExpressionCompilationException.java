/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Indicates than an error occurred while compiling an expression.
 *
 * @since 1.3
 */
public final class ExpressionCompilationException extends MuleRuntimeException {

  public ExpressionCompilationException(I18nMessage message) {
    super(message);

  }
}
