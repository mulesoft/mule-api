/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import org.mule.runtime.api.el.validation.ValidationMessage;

import java.util.List;
import java.util.Optional;

public final class DefaultValidationResult implements ValidationResult {

  private boolean result;
  private String errorMessage;
  private List<ValidationMessage> messages;

  public DefaultValidationResult(boolean result, String errorMessage) {
    this(result, errorMessage, emptyList());
  }

  public DefaultValidationResult(boolean result, String errorMessage, List<ValidationMessage> messages) {
    this.result = result;
    this.errorMessage = errorMessage;
    this.messages = unmodifiableList(messages);
  }

  @Override
  public Optional<String> errorMessage() {
    return Optional.ofNullable(errorMessage);
  }

  @Override
  public List<ValidationMessage> messages() {
    return messages;
  }

  @Override
  public boolean isSuccess() {
    return result;
  }
}
