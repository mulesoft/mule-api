/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation.constraints;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.runtime.api.el.validation.GenericConstraintViolation;
import org.mule.runtime.api.el.validation.Location;
import org.mule.runtime.api.el.validation.Severity;

import java.util.Optional;

/**
 * A typed constraint violation triggered when a script is returning an object value with an extra property that is not defined in
 * a closed object output type definition. In appends information about the extra property, and the
 * {@link TypeIdAnnotation#getValue()} (if provided as part of the output type).
 *
 * @since 1.9.0
 */
public class CloseDoesNotAllowExtraPropertiesConstraintValidation extends GenericConstraintViolation {

  private String property;
  private String typeId;

  public CloseDoesNotAllowExtraPropertiesConstraintValidation(String kind,
                                                              String message,
                                                              Location location,
                                                              Severity severity,
                                                              String property,
                                                              String typeId) {
    super(severity, kind, message, location);
    this.property = property;
    this.typeId = typeId;
  }

  /**
   * @return The extra property not allowed.
   */
  public String getProperty() {
    return property;
  }

  /**
   * @return The typeId associated to the type where the extra property was found.
   */
  public Optional<String> getTypeId() {
    return Optional.ofNullable(typeId);
  }
}
