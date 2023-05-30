/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message.error.matcher;

import org.mule.runtime.api.message.ErrorType;

/**
 * Decides whether an error type matches a criteria defined by the implementation.
 *
 * @since 1.6
 */
public interface ErrorTypeMatcher {

  /**
   * @param errorType the {@link ErrorType} to check
   * @return {@code true} if a match is possible, {@code false} otherwise
   */
  boolean match(ErrorType errorType);

}
