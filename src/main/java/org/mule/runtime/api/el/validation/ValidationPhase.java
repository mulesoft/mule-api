/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import org.mule.metadata.message.api.el.TypeBindings;

import java.util.Optional;

/**
 * The scope for the validation, see
 * {@link org.mule.runtime.api.el.ExpressionLanguage#validate(String, String, ValidationPhase, TypeBindings, Optional)} for more
 * information.
 *
 * @since 1.9.0
 */
public enum ValidationPhase {
  /**
   * Phase that only validates that the script is parsed correctly. Checks for syntax.
   */
  PARSING,
  /**
   * Phase that applies the {@link ValidationPhase#PARSING} and also validates references are resolved correctly.
   */
  SCOPE,
  /**
   * Phase that applies the {@link ValidationPhase#SCOPE} and also validates type constratins.
   */
  TYPE
}
