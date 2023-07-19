/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.function;

import org.mule.api.annotation.NoImplement;

import java.util.List;
import java.util.Optional;

/**
 * A model which contains {@link FunctionModel}s
 *
 * @since 1.0
 */
@NoImplement
public interface HasFunctionModels {

  /**
   * Returns a {@link List} of {@link FunctionModel}s defined at the level of the component implementing this interface.
   *
   * Each function is guaranteed to have a unique name which will not overlap with any other component defined at any level.
   *
   * @return an immutable {@link List} of {@link FunctionModel}
   */
  List<FunctionModel> getFunctionModels();

  /**
   * Returns the {@link FunctionModel} that matches the given name.
   *
   * @param name case sensitive operation name
   * @return an {@link Optional} {@link FunctionModel}
   */
  Optional<FunctionModel> getFunctionModel(String name);
}
