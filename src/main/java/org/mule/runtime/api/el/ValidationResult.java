/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.el.validation.ValidationMessage;

import java.util.List;
import java.util.Optional;

/**
 * Represents the result of an expression validation.
 *
 * @since 1.0
 */
@NoImplement
public interface ValidationResult {

  static ValidationResult success() {
    return new DefaultValidationResult(true, null);
  }

  static ValidationResult failure(String message) {
    return new DefaultValidationResult(false, message);
  }

  static ValidationResult failure(String message, List<ValidationMessage> messages) {
    return new DefaultValidationResult(false, message, messages);
  }

  static ValidationResult failure(String message, String expression) {
    return failure(String.format("%s. Offending expression string is: %s", message, expression));
  }

  /**
   * @return an optional representing the generic validation error or an empty one
   */
  Optional<String> errorMessage();

  /**
   * @return a list of all {@link ValidationMessage}s found or an empty list if no relevant data is present
   */
  List<ValidationMessage> messages();

  /**
   * @return true if the validation was ok, false otherwise
   */
  boolean isSuccess();

}
