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
 * Indicates than an error occurred while compiling an expression.
 *
 * @since 1.3
 */
public final class ExpressionCompilationException extends MuleRuntimeException {

  public ExpressionCompilationException(I18nMessage message) {
    super(message);

  }
}
