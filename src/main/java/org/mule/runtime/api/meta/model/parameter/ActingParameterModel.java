/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.meta.model.data.sample.SampleDataProviderModel;

/**
 * An acting parameter of a {@link ValueProviderModel} or {@link SampleDataProviderModel}
 * <p>
 * An acting parameter provides a name, a default value and indicates if it is required
 * </p>
 *
 * @since 4.4.0
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
   * Gets the default value.
   *
   * @return the default value
   */
  Object getDefaultValue();
}
