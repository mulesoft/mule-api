/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import java.util.Set;

/**
 * A declaration object to indicate that parameters in a {@link ParameterizedDeclaration} are mutually exclusive
 *
 * @since 1.0
 */
public final class ExclusiveParametersDeclaration {

  /**
   * The names of the mutually exclusive parameters
   */
  private final Set<String> parameterNames;

  /**
   * Indicates if besides of being exclusive, the runtime should enforce that on of the parameters MUST be provided
   */
  private final boolean requiresOne;

  /**
   * Creates a new instance
   *
   * @param parameterNames the parameter names
   * @param requiresOne    whether one is required
   */
  public ExclusiveParametersDeclaration(Set<String> parameterNames, boolean requiresOne) {
    this.parameterNames = parameterNames;
    this.requiresOne = requiresOne;
  }

  public Set<String> getParameterNames() {
    return parameterNames;
  }

  public boolean isRequiresOne() {
    return requiresOne;
  }
}
