/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;


/**
 * Provides universal access for evaluating expressions.
 * 
 * @since 1.0
 */
public interface ExpressionEvaluator {

  /**
   * Registers the given {@link BindingContext} entries as globals. Notice globals cannot be removed once registered, only
   * overwritten by the registration of a binding with the same identifier.
   *
   * @param bindingContext the context to register
   */
  void addGlobalContext(BindingContext bindingContext);

  /**
   * Evaluates an expression according to a given {@link BindingContext} and the global one.
   *
   * @param expression the EL expression
   * @param context the current dynamic binding context to consider
   * @return the result of the expression plus its type
   * @throws ExpressionExecutionException when an error occurs during evaluation
   */
  TypedValue evaluate(String expression, BindingContext context) throws ExpressionExecutionException;

  /**
   * Evaluates an expression according to a given {@link BindingContext}, the global one and the {@link DataType} of the expected
   * result.
   *
   * @param expression the EL expression
   * @param expectedOutputType the expected output type so that automatic conversion can be performed for the resulting value
   *        type.
   * @param context an expression binding context to consider
   * @return the result of the expression plus its type
   * @throws ExpressionRuntimeException or during transformation or during transformation
   */
  TypedValue evaluate(String expression, DataType expectedOutputType, BindingContext context) throws ExpressionExecutionException;

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
  boolean isValid(String expression);

  /**
   * Verifies whether an expression is valid or not syntactically.
   *
   * @param expression to be validated
   * @return a {@link ValidationResult} indicating whether the validation was successful or not
   */
  ValidationResult validate(String expression);

}
