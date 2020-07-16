/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.operation;

/**
 * Determines that an error thrown by an operation should be mapped to another.
 *
 * @since 1.4
 */
public interface ErrorMapping {

  /**
   * @return the type of the error to be mapped from
   */
  String getSource();

  /**
   * @return the type of the error to be mapped to
   */
  String getTarget();

}
