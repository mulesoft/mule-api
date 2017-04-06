/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

/**
 * Provides universal access for evaluating mule expressions #[].
 * And delegates the execution to the {@link DefaultExpressionLanguageFactoryService}
 *
 * @since 1.0
 */
public interface MuleExpressionLanguage extends ExpressionLanguage {


  /**
   * Determines if the string is an expression.
   *
   * @param expression is this string an expression string
   * @return true if the string contains an expression
   */
  boolean isExpression(String expression);

  /**
   * Validates the expression returning true is the expression is valid, false otherwise.. All implementors should should validate
   * expression syntactically. Semantic validation is optional.
   *
   * @param expression
   * @return true if the expression is valid.
   */
  default boolean isValid(String expression) {
    return validate(expression).isSuccess();
  }

}
