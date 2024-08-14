/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.validation.constraints;

import static org.mule.runtime.api.el.validation.Severity.ERROR;

import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.runtime.api.el.validation.GenericConstraintViolation;
import org.mule.runtime.api.el.validation.Location;

import java.util.List;
import java.util.Optional;

/**
 * A typed constraint violation triggered when a script is returning an object value with an extra property that is not defined in
 * a closed object output type definition. In appends information about the extra property, and the
 * {@link TypeIdAnnotation#getValue()} (if provided as part of the output type).
 *
 * @since 1.9.0
 */
public class PropertyNotDefinedConstraintValidation extends GenericConstraintViolation {

  private String property;
  private List<String> options;
  private String typeId;

  public PropertyNotDefinedConstraintValidation(String kind,
                                                String message,
                                                Location location,
                                                String property,
                                                List<String> options,
                                                String typeId) {
    super(ERROR, kind, message, location);
    this.property = property;
    this.options = options;
    this.typeId = typeId;
  }

  /**
   * @return The property not found.
   */
  public String getProperty() {
    return property;
  }

  /**
   * @return The {@link List} of properties option available defined for the type definition of the object.
   */
  public List<String> getOptions() {
    return options;
  }

  /**
   * @return The typeId associated to the type where the property was found.
   */
  public Optional<String> getTypeId() {
    return Optional.ofNullable(typeId);
  }
}
