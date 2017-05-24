/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.service.Service;

import java.util.Iterator;

/**
 * Evaluates an expression considering a set of given bindings and a set of global ones.
 *
 * @since 1.0
 */
public interface ExpressionLanguage {

  /**
   * Includes the bindings in a given {@link BindingContext} as global ones, that should not change often and should be considered
   * for all subsequent operations.
   *
   * @param bindingContext the context containing the bindings to add
   */
  void addGlobalBindings(BindingContext bindingContext);

  /**
   * Evaluates an expression according to a given {@link BindingContext} and the global one.
   *
   * @param expression the EL expression
   * @param context    the current dynamic binding context to consider
   * @return the result of the expression plus its type
   * @throws ExpressionExecutionException when an error occurs during evaluation
   */
  TypedValue<?> evaluate(String expression, BindingContext context) throws ExpressionExecutionException;


  /**
   * Evaluates an expression according to a given {@link BindingContext}, the global one and the {@link DataType} of the expected
   * result.
   *
   * @param expression         the EL expression
   * @param expectedOutputType the expected output type so that automatic conversion can be performed for the resulting value
   *                           type.
   * @param context            the current dynamic binding context to consider
   * @return the result of the expression plus its type
   * @throws ExpressionExecutionException or during transformation or during transformation
   */
  TypedValue<?> evaluate(String expression, DataType expectedOutputType, BindingContext context)
      throws ExpressionExecutionException;

  /**
   * Verifies whether an expression is valid or not syntactically.
   *
   * @param expression to be validated
   * @return a {@link ValidationResult} indicating whether the validation was successful or not
   */
  ValidationResult validate(String expression);

  /**
   * Splits using the specified expression. The expression should return a collection of elements or an object.
   * In case of the object it will iterate through the entries
   *
   * @param expression the expression to be used to split
   * @param context    the current dynamic binding context to consider
   * @return an iterator with the elements that were split
   */
  Iterator<TypedValue<?>> split(String expression, BindingContext context);
}
