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
