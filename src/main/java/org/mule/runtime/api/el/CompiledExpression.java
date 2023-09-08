/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import org.mule.runtime.api.metadata.MediaType;

import java.util.List;
import java.util.Optional;

/**
 * Represents a compiled expression
 *
 * @since 1.3
 */
public interface CompiledExpression {

  /**
   * Returns the expression that generates this compiled expression
   *
   * @return The expression
   */
  String expression();


  /**
   * Returns the output type of this compiled expression
   *
   * @return The output type
   */
  Optional<MediaType> outputType();


  /**
   * Returns the list of external variable references
   *
   * @return The list of external variable references
   */
  List<ModuleElementName> externalDependencies();


  default String nameIdentifier() {
    return "Anonymous";
  }


}
