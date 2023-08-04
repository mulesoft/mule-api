/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.validation;

/**
 * Represents a message referring to a performed validation, with a {@link Severity}, a {@link Location} and a generic message.
 *
 * @since 1.0
 */
public final class ValidationMessage {

  private Severity severity;
  private String message;
  private Location location;

  public ValidationMessage(Severity severity, String message, Location location) {
    this.severity = severity;
    this.message = message;
    this.location = location;
  }

  public Severity getSeverity() {
    return severity;
  }

  public String getMessage() {
    return message;
  }

  public Location getLocation() {
    return location;
  }
}
