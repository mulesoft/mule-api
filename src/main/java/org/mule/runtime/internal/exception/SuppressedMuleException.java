/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.exception;

import org.mule.runtime.api.exception.MuleException;

/**
 * @see org.mule.runtime.privileged.exception.SuppressedMuleException
 * @since 1.2.3, 1.3
 * @deprecated Replaced by {@link org.mule.runtime.privileged.exception.SuppressedMuleException}.
 */
public class SuppressedMuleException extends org.mule.runtime.privileged.exception.SuppressedMuleException {

  private static final long serialVersionUID = -2020531237382360468L;

  /**
   * Constructs a new {@link SuppressedMuleException}
   *
   * @param throwable       Exception that will be wrapped.
   * @param causeToSuppress The cause that wants to be suppressed. Cannot be null.
   */
  protected SuppressedMuleException(Throwable throwable, MuleException causeToSuppress) {
    super(throwable, causeToSuppress);
  }
}
