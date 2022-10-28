/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import static java.util.Collections.emptyList;

import java.util.List;

/**
 * Default implementation for validation scope result.
 *
 * @since 1.5.0
 */
public class DefaultScopeValidationResult implements ScopeValidationResult {

  private final boolean success;
  private List<ValidationNotification> errors = emptyList();
  private List<ValidationNotification> warnings = emptyList();

  public DefaultScopeValidationResult(boolean success) {
    this.success = success;
  }

  public DefaultScopeValidationResult(boolean success, List<ValidationNotification> errors,
                                      List<ValidationNotification> warnings) {
    this.success = success;
    this.errors = errors;
    this.warnings = warnings;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public List<ValidationNotification> errors() {
    return errors;
  }

  public List<ValidationNotification> warnings() {
    return warnings;
  }
}
