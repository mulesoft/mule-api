/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import org.mule.api.annotation.NoImplement;

import static java.util.Collections.emptyList;

import java.util.List;

/**
 * Represents the result of the scope validation
 *
 * @since 1.5.0
 */
@NoImplement
public interface ScopeValidationResult {

  static ScopeValidationResult success() {
    return new DefaultScopeValidationResult(true);
  }

  static ScopeValidationResult success(List<ValidationNotification> warnings) {
    return new DefaultScopeValidationResult(true, emptyList(), warnings);
  }

  static ScopeValidationResult failure(List<ValidationNotification> errors) {
    return new DefaultScopeValidationResult(false, errors, emptyList());
  }

  static ScopeValidationResult failure(List<ValidationNotification> errors, List<ValidationNotification> warnings) {
    return new DefaultScopeValidationResult(false, errors, warnings);
  }

  /**
   * @return true if the validation was ok, false otherwise
   */
  boolean isSuccess();

  /**
   * @return a list of {@link ValidationNotification} errors notifications while scope validation
   */
  List<ValidationNotification> errors();

  /**
   * @return a list of {@link ValidationNotification} warnings notifications while scope validation
   */
  List<ValidationNotification> warnings();
}
