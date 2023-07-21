/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.message.error.matcher;

import org.mule.runtime.api.message.ErrorType;

/**
 * Decides whether an error type matches a criteria defined by the implementation.
 *
 * @since 1.5
 */
public interface ErrorTypeMatcher {

  /**
   * @param errorType the {@link ErrorType} to check
   * @return {@code true} if a match is possible, {@code false} otherwise
   */
  boolean match(ErrorType errorType);

}

