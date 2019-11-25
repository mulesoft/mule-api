/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.metadata.TypedValue;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
   * @throws ExpressionExecutionException when an error occurs during evaluation or while parsing
   */
  TypedValue<?> evaluate(String expression, DataType expectedOutputType, BindingContext context)
      throws ExpressionExecutionException;

  /**
   * Evaluates an expression according to a given {@link BindingContext} and the global one.
   *
   * The evaluation of this script will do a best effort to avoid failing when the result value can not be represented in the corresponding format.
   *
   * @param expression         the EL expression
   * @param context            the current dynamic binding context to consider
   * @return the result of the expression plus its type
   * @throws ExpressionExecutionException when an error occurs during evaluation
   */
  TypedValue<?> evaluateLogExpression(String expression, BindingContext context)
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

  /**
   * Compiles a specified expression with a given binding context and returns
   *
   * @param expression The expression
   * @param context    The context to compile against
   * @return The compiled expression
   * @throws ExpressionCompilationException when an error occurs during compilation
   * @since 1.3
   */
  default CompiledExpression compile(String expression, BindingContext context) throws ExpressionCompilationException {
    return new CompiledExpression() {

      @Override
      public String expression() {
        return expression;
      }

      @Override
      public Optional<MediaType> outputType() {
        return Optional.empty();
      }

      @Override
      public List<ModuleElementName> externalDependencies() {
        return Collections.emptyList();
      }
    };
  }

  /**
   * Returns an object that caches computation results. Provides better performance when evaluation multiple expressions on the
   * same bindings.
   * <p>
   * Sessions obtained through this method have to be explicitly {@link ExpressionLanguageSession#close() closed}.
   *
   * @since 1.2
   *
   * @param context the current dynamic binding context to consider
   * @return a session associated to the provided {@code context}
   */
  default ExpressionLanguageSession openSession(BindingContext context) {
    ExpressionLanguage expressionLanguage = this;
    return new ExpressionLanguageSession() {

      @Override
      public TypedValue<?> evaluate(String expression) throws ExpressionExecutionException {
        return expressionLanguage.evaluate(expression, context);
      }

      @Override
      public TypedValue<?> evaluate(String expression, DataType expectedOutputType) throws ExpressionExecutionException {
        return expressionLanguage.evaluate(expression, expectedOutputType, context);
      }

      @Override
      public TypedValue<?> evaluate(String expression, long timeout) throws ExpressionExecutionException {
        return expressionLanguage.evaluate(expression, context);
      }

      @Override
      public TypedValue<?> evaluateLogExpression(String expression) throws ExpressionExecutionException {
        return expressionLanguage.evaluateLogExpression(expression, context);
      }

      @Override
      public Iterator<TypedValue<?>> split(String expression) {
        return expressionLanguage.split(expression, context);
      }

      @Override
      public TypedValue<?> evaluate(CompiledExpression expression) throws ExpressionExecutionException {
        return evaluate(expression.expression());
      }

      @Override
      public TypedValue<?> evaluate(CompiledExpression expression, DataType expectedOutputType)
          throws ExpressionExecutionException {
        return evaluate(expression.expression(), expectedOutputType);
      }

      @Override
      public TypedValue<?> evaluate(CompiledExpression expression, long timeout) throws ExpressionExecutionException {
        return evaluate(expression.expression(), timeout);
      }

      @Override
      public TypedValue<?> evaluateLogExpression(CompiledExpression expression) throws ExpressionExecutionException {
        return evaluateLogExpression(expression.expression());
      }

      @Override
      public Iterator<TypedValue<?>> split(CompiledExpression expression) {
        return split(expression.expression());
      }

      @Override
      public void close() {
        // Nothing to do
      }
    };
  }
}
