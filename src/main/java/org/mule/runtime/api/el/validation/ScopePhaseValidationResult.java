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
 * Represents the result of the scope validation
 *
 * @since 1.5.0
 */
public final class ScopePhaseValidationResult {

  public static ScopePhaseValidationResult success() {
    return new ScopePhaseValidationResult(true);
  }

  public static ScopePhaseValidationResult success(List<ScopePhaseValidationItem> warnings) {
    return new ScopePhaseValidationResult(true, emptyList(), warnings);
  }

  public static ScopePhaseValidationResult failure(List<ScopePhaseValidationItem> errors) {
    return new ScopePhaseValidationResult(false, errors, emptyList());
  }

  public static ScopePhaseValidationResult failure(List<ScopePhaseValidationItem> errors,
                                                   List<ScopePhaseValidationItem> warnings) {
    return new ScopePhaseValidationResult(false, errors, warnings);
  }

  private final boolean success;
  private List<ScopePhaseValidationItem> errors = emptyList();
  private List<ScopePhaseValidationItem> warnings = emptyList();

  private ScopePhaseValidationResult(boolean success) {
    this.success = success;
  }

  private ScopePhaseValidationResult(boolean success, List<ScopePhaseValidationItem> errors,
                                     List<ScopePhaseValidationItem> warnings) {
    this.success = success;
    this.errors = errors;
    this.warnings = warnings;
  }

  /**
   * @return true if the validation was ok, false otherwise
   */
  public boolean isSuccess() {
    return this.success;
  }

  /**
   * @return a list of {@link ScopePhaseValidationItem} errors items while scope validation
   */
  public List<ScopePhaseValidationItem> getErrors() {
    return errors;
  }

  /**
   * @return a list of {@link ScopePhaseValidationItem} warnings items while scope validation
   */
  public List<ScopePhaseValidationItem> getWarnings() {
    return warnings;
  }
}
