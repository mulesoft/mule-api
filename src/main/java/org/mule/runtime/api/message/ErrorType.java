/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.api.annotation.NoImplement;

import java.io.Serializable;

/**
 * An {@code ErrorType} describes an type of error that may be thrown by a mule component.
 * <p>
 * The error type has a string representation {@see getIdentifier} which is used directly by the user in the mule configuration.
 * <p>
 * Every error belongs to a namespace {@see getNamespace} in order to avoid collisions of error with the same string
 * representation but that belong to different namespace {@see getNamespace}.
 * <p>
 * Error types may be an specialization of a more general error type in which case the {@code getParentErrorType} should return
 * the more general error type. This is used when doing error type matching within error handlers so when selecting the general
 * error type for error handling it will also handle the more specialized error types.
 */
@NoImplement
public interface ErrorType extends Serializable {

  /**
   * Identifier of the error. Is the value that is meant to be used in the configuration.
   *
   * @return the string representation of the error.
   */
  String getIdentifier();

  /**
   * The namespace of the module where the error is defined. For instance, for runtime errors the namespace is core.
   *
   * @return namespace of the module that defines this error.
   */
  String getNamespace();

  /**
   * An error can be an specific type of a more general error type in which case it must return the general error as result of
   * calling {@code getParentErrorType}
   *
   * @return the parent error type.
   */
  ErrorType getParentErrorType();


}
