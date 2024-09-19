/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation;

import static org.mule.runtime.api.el.validation.Severity.ERROR;
import static org.mule.runtime.api.el.validation.Severity.WARNING;

/**
 * A generic {@link ConstraintViolation} implementation.
 *
 * Typed constraints violations that need to be extended in order to expose more data about the violation would need to extend
 * this class and add the additional fields.
 *
 * @since 1.9.0
 */
public class GenericConstraintViolation implements ConstraintViolation {

  private Severity severity;
  private String kind;
  private String message;
  private Location location;

  /**
   * Creates a {@link Severity#WARNING} constraint violation.
   *
   * @param kind     The kind of constraint violation.
   * @param message  A message describing the constraint violated.
   * @param location The {@link Location} in the script where the constraint violation happened.
   * @return A {@link ConstraintViolation}.
   */
  public static ConstraintViolation warning(String kind, String message, Location location) {
    return new GenericConstraintViolation(WARNING, kind, message, location);
  }

  /**
   * Creates an {@link Severity#ERROR} constraint violation.
   *
   * @param kind     The kind of constraint violation.
   * @param message  A message describing the constraint violated.
   * @param location The {@link Location} in the script where the constraint violation happened.
   * @return A {@link ConstraintViolation}.
   */
  public static ConstraintViolation error(String kind, String message, Location location) {
    return new GenericConstraintViolation(ERROR, kind, message, location);
  }


  protected GenericConstraintViolation(Severity severity, String kind, String message, Location location) {
    this.severity = severity;
    this.kind = kind;
    this.message = message;
    this.location = location;
  }

  @Override
  public Severity getSeverity() {
    return this.severity;
  }

  @Override
  public String getKind() {
    return this.kind;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public Location getLocation() {
    return this.location;
  }

}
