/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.interception;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.exception.MuleRuntimeException;

/**
 * Contains information about a single parameter of an intercepted component.
 *
 * @since 1.0
 */
@NoImplement
public interface ProcessorParameterValue {

  /**
   * @return the name of the parameter, as defined in the DSL.
   */
  String parameterName();

  /**
   * Allows to query the unresolved value originally configured in the application.
   *
   * @return the value as a {@link String}, as it is configured in the application configuration, or {@code null} for implicit
   *         operation parameters.
   */
  String providedValue();

  /**
   * Performs any required expression resolution on {@link #providedValue()}.
   *
   * @return if {@link #providedValue()} was an expression, its resolved value. Otherwise, the same as {@link #providedValue()}.
   * @throws MuleRuntimeException if an error occurs while resolving the value.
   */
  Object resolveValue();

}
