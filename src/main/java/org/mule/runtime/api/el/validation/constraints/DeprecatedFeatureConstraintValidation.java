/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation.constraints;

import static org.mule.runtime.api.el.validation.Severity.WARNING;

import org.mule.runtime.api.el.validation.GenericConstraintViolation;
import org.mule.runtime.api.el.validation.Location;

/**
 * A typed constraint violation triggered when an element annotated with {@link @Deprecated} is used in a script. It appends
 * information about the element where deprecated, its replacement and since version is deprecated.
 *
 * @since 1.9.0
 */
public class DeprecatedFeatureConstraintValidation extends GenericConstraintViolation {

  private String name;
  private String replacement;
  private String since;

  public DeprecatedFeatureConstraintValidation(String kind,
                                               String message,
                                               Location location,
                                               String name,
                                               String replacement,
                                               String since) {
    super(WARNING, kind, message, location);
    this.name = name;
    this.replacement = replacement;
    this.since = since;
  }

  /**
   * @return The name of the deprecated element (function, variable).
   */
  public String getName() {
    return name;
  }

  /**
   * @return
   */
  public String getReplacement() {
    return replacement;
  }

  /**
   * @return The value indicating the expression language version since this element is deprecated.
   */
  public String getSince() {
    return since;
  }
}
