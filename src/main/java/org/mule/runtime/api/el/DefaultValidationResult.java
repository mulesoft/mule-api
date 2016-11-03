/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.util.Optional;

public class DefaultValidationResult implements ValidationResult {

  private boolean result;
  private String errorMessage;

  public DefaultValidationResult(boolean result, String errorMessage) {
    this.result = result;
    this.errorMessage = errorMessage;
  }

  @Override
  public Optional<String> errorMessage() {
    return Optional.ofNullable(errorMessage);
  }

  @Override
  public boolean isSuccess() {
    return result;
  }
}
