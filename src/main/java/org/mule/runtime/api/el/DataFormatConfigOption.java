/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el;

import java.util.List;
import java.util.Optional;

/**
 * Describes a DataFormat configuration option. Used to configure the reader and writers
 *
 * @since 1.2.0
 */
public class DataFormatConfigOption {

  private String name;
  private ConfigOptionType type;
  private Object defaultValue;
  private String description;
  private List<Object> possibleValues;

  public DataFormatConfigOption(String name, ConfigOptionType type, Object defaultValue, String description,
                                List<Object> possibleValues) {
    this.name = name;
    this.type = type;
    this.defaultValue = defaultValue;
    this.description = description;
    this.possibleValues = possibleValues;
  }

  /**
   * Returns the name of the configuration option
   *
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the type of this option.
   *
   * @return The type of this option
   */
  public ConfigOptionType getType() {
    return type;
  }

  /**
   * Returns the default value is any
   *
   * @return The default value
   */
  public Optional<Object> getDefaultValue() {
    return Optional.ofNullable(defaultValue);
  }

  /**
   * Retursn the description of this option
   *
   * @return The description
   */
  public String getDescription() {
    return description;
  }

  /**
   * The list of possible values for this option. If the list is empty means that no constrain was set.
   *
   * @return The list of possible values
   */
  public List<Object> getPossibleValues() {
    return possibleValues;
  }

  /**
   * The list of available configuration types.
   */
  enum ConfigOptionType {
    String, Number, Boolean
  }
}
