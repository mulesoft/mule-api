/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.metadata;

import org.mule.api.annotation.NoImplement;

import java.util.List;
import java.util.Optional;

/**
 * A data type that represents a function, with it's optional return type and parameter types.
 *
 * @since 1.0
 */
@NoImplement
public interface FunctionDataType extends DataType {

  /**
   * @return a list of {@link FunctionParameter}s representing the function's.
   */
  List<FunctionParameter> getParameters();

  /**
   * @return the {@link DataType} of the function's return type if available, otherwise an empty {@link Optional}
   */
  Optional<DataType> getReturnType();

}
