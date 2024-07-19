/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import static java.lang.String.format;

/**
 * Utilities for using {@link ExpressionLanguage} instances
 *
 * @since 1.8.0
 */
public final class ExpressionLanguageUtils {

  public static String DEFAULT_EXPRESSION_PREFIX = "#[";
  public static String DEFAULT_EXPRESSION_POSTFIX = "]";

  private ExpressionLanguageUtils() {}

  /**
   * Returns a sanitized version of the given {@code expression}
   *
   * @param expression the expression to sanitize
   * @return the sanitized expression
   */
  public static String sanitize(String expression) {
    String sanitizedExpression;
    if (expression.startsWith(DEFAULT_EXPRESSION_PREFIX)) {
      if (!expression.endsWith(DEFAULT_EXPRESSION_POSTFIX)) {
        throw new ExpressionExecutionException(createStaticMessage(format("Unbalanced brackets in expression '%s'", expression)));
      }
      sanitizedExpression =
          expression.substring(DEFAULT_EXPRESSION_PREFIX.length(), expression.length() - DEFAULT_EXPRESSION_POSTFIX.length());
    } else {
      sanitizedExpression = expression;
    }

    return sanitizedExpression;
  }
}
