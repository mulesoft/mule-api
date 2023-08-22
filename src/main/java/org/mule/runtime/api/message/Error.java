/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message;

import org.mule.api.annotation.NoImplement;

import java.io.Serializable;
import java.util.List;

@NoImplement
public interface Error extends Serializable {

  /**
   * Concise description of the error. For more information {@see #getDetailedDescription}
   *
   * @return a concise description of the error.
   */
  String getDescription();

  /**
   * Detailed description of the error. This message may include Java exception specific information.
   *
   * @return a details description of the error.
   */
  String getDetailedDescription();

  /**
   * @return The component where this error was generated
   *
   * @since 1.3
   */
  default String getFailingComponent() {
    return null;
  }

  /**
   * Returns the type of the error. @see {@link ErrorType}.
   *
   * @return the type of the error
   */
  ErrorType getErrorType();

  /**
   * The Java exception thrown by the failing component.
   *
   * @return the exception thrown by the failing component.
   */
  Throwable getCause();

  /**
   * This is an error response generated by the component that failed to process the message. For instance, HTTP connector may
   * generate an error response with the content of the HTTP response if the failure was because of an invalid status code.
   *
   * Not all failing components generate an error response so this field may be null.
   *
   * @return the message with the error data.
   */
  Message getErrorMessage();

  /**
   * Lists any child {@link Error Errors}, if any. For instance, the scatter-gather router may throw an error aggregating all of
   * its routes errors as children.
   *
   * Not all failing components aggregate errors so this may return an empty collection.
   *
   * @return a list with all the aggregated errors
   */
  List<Error> getChildErrors();

}
