/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import org.mule.api.annotation.NoImplement;

/**
 * An acting parameter of a design time tooling capability.
 * <p>
 * An acting parameter provides a name and indicates if it is required
 * </p>
 *
 * @since 1.4.0
 */
@NoImplement
public interface ActingParameterModel {

  /**
   * Gets the name
   *
   * @return the name
   */
  String getName();

  /**
   * Whether or not this parameter is required.
   *
   * @return a boolean value saying if this parameter is required or not
   */
  boolean isRequired();

  /**
   * @return the extraction expression to the acting parameter’s value in the component.
   */
  String getExtractionExpression();
}
