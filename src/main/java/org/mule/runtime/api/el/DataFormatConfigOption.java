/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el;

import java.util.List;
import java.util.Optional;

/**
 * Describes a DataFormat configuration option. Used to configure the reader and writers
 *
 * @since 1.2.0
 */
public interface DataFormatConfigOption {

  /**
   * Returns the name of the configuration option
   *
   * @return The name
   */
  String getName();

  /**
   * Returns the type of this option.
   *
   * @return The type of this option
   */
  ConfigOptionType getType();

  /**
   * Returns the default value is any
   *
   * @return The default value
   */
  Optional<Object> getDefaultValue();

  /**
   * Retursn the description of this option
   *
   * @return The description
   */
  String getDescription();

  /**
   * The list of possible values for this option. If the list is empty means that no constrain was set.
   *
   * @return The list of possible values
   */
  List<Object> getPossibleValues();

  /**
   * The list of available configuration types.
   */
  enum ConfigOptionType {
    String, Number, Boolean
  }
}
