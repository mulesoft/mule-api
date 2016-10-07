/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.message.MuleEvent;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;

/**
 * Evaluates an expression considering a given context and a global one, via an {@link ExpressionExecutor}.
 *
 * @since 4.0
 */
public interface ExpressionLanguage {

  /**
   * Evaluates an expression according to the global {@link BindingContext}
   *
   * @param expression the EL expression
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression);

  /**
   * Evaluates an expression according to a given {@link BindingContext} and the global one
   *
   * @param expression the EL expression
   * @param context an expression binding context to consider
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression, BindingContext context);

  /**
   * Evaluates an expression according to a given {@link BindingContext}, the global one and an {@link MuleEvent}.
   *
   * @param expression the EL expression
   * @param context an expression binding context to consider
   * @param event the current event to consider
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression, BindingContext context, MuleEvent event);

  /**
   * Evaluates an expression according to the global {@link BindingContext} and the {@link DataType} of the expected result.
   *
   * @param expression the EL expression
   * @param expectedOutputType the expected output type so that the EL can do automatic conversion for the resulting value type.
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression, DataType expectedOutputType);

  /**
   * Evaluates an expression according to a given {@link BindingContext}, the global one and the {@link DataType} of the expected result.
   *
   * @param expression the EL expression
   * @param expectedOutputType the expected output type so that the EL can do automatic conversion for the resulting value type.
   * @param context an expression binding context to consider
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression, DataType expectedOutputType, BindingContext context);

  /**
   * Evaluates an expression according to a given {@link BindingContext}, the global one, the {@link DataType} of the expected result and an {@link MuleEvent}.
   *
   * @param expression the EL expression
   * @param expectedOutputType the expected output type so that the EL can do automatic conversion for the resulting value type.
   * @param context an expression binding context to consider
   * @param event the current event to consider
   * @return the result of the expression plus its type
   */
  TypedValue evaluate(String expression, DataType expectedOutputType, BindingContext context, MuleEvent event);

  /**
   * Verifies whether an expression is valid or not syntactically.
   *
   * @param expression to be validated
   * @return a {@link ValidationResult} indicating whether the validation was successful or not
   */
  ValidationResult validate(String expression);

}
