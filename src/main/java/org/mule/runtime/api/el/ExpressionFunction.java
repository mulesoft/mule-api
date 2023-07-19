/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import java.util.List;
import java.util.Optional;

import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.FunctionParameter;

/**
 * Representation of a function to be used by an {@link ExpressionLanguage}. Allows to define the parameter list expected and the
 * return type so that validations can be applied before and after execution by the {@link ExpressionLanguage}. Thus, it is not
 * necessary for implementations to validate the input.
 *
 * @since 1.0
 */
public interface ExpressionFunction {

  /**
   * Function logic to be executed.
   *
   * @param parameters array of parameters that should match the indicated ones in {@link #parameters()}.
   * @param context    the current {@link BindingContext} at the time of execution.
   * @return the function outcome that should match the one indicated in {@link #returnType()}.
   */
  Object call(Object[] parameters, BindingContext context);

  /**
   * Indicates the return type, if any. Notice that it should match the returned {@link Object} in
   * {@link #call(Object[], BindingContext)} and be empty if {@code null} is returned. This will be validated before execution.
   *
   * @return an {@link Optional} {@link DataType}
   */
  Optional<DataType> returnType();

  /**
   * Indicates the parameters this function takes. Notice that the order and amount should match the expected ones in
   * {@link #call(Object[], BindingContext)}. This will be validated before execution.
   *
   * @return a {@link List} of {@link FunctionParameter}s
   */
  List<FunctionParameter> parameters();

}
