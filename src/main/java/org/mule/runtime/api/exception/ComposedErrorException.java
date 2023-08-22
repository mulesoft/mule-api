/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.message.Error;

import java.util.List;

/**
 * Exception that holds a collection of child {@link Error Errors}. When this exception is analysed to create an
 * {@link org.mule.runtime.api.message.Error}, it will feature the collection.
 *
 * @since 1.0
 */
public interface ComposedErrorException {

  /**
   * Provides the list of errors associated to this exception.
   *
   * @return a list of child errors
   */
  List<Error> getErrors();

}
