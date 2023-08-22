/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.message.error.matcher;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.message.ErrorType;

/**
 * Simple implementation of {@link ErrorTypeMatcher} that just checks if this matches the given {@link ErrorType} or an ancestor
 * one.
 * 
 * @since 1.5
 */
@NoImplement
@NoExtend
public class SingleErrorTypeMatcher implements ErrorTypeMatcher {

  private final ErrorType errorType;

  public SingleErrorTypeMatcher(ErrorType errorType) {
    this.errorType = errorType;
  }

  @Override
  public boolean match(ErrorType error) {
    return this.errorType.equals(error) || isChild(error);
  }

  private boolean isChild(ErrorType error) {
    ErrorType parentErrorType = error.getParentErrorType();
    return parentErrorType != null && this.match(parentErrorType);
  }
}
