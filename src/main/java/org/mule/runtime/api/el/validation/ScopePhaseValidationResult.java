/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
public interface ScopePhaseValidationResult {

  /**
   * @return a list of {@link ScopePhaseValidationItem} errors items while scope validation
   */
  List<ScopePhaseValidationItem> getErrors();

  /**
   * @return a list of {@link ScopePhaseValidationItem} warnings items while scope validation
   */
  List<ScopePhaseValidationItem> getWarnings();
}
