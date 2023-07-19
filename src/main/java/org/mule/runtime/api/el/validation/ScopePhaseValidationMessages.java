/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.validation;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * Represents the result of the scope validation
 *
 * @since 1.5.0
 */
@NoImplement
public interface ScopePhaseValidationMessages {

  /**
   * @return a list of {@link ScopePhaseValidationItem} errors items while scope validation
   */
  List<ScopePhaseValidationItem> getErrors();

  /**
   * @return a list of {@link ScopePhaseValidationItem} warnings items while scope validation
   */
  List<ScopePhaseValidationItem> getWarnings();
}
