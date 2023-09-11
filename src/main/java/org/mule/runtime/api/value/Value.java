/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.value;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Represents a possible and valid value for a parameter.
 * <p>
 * Also this {@link Value} can be composed by other {@link Value Options} to form a composed option.
 *
 * @since 1.0
 */
@NoImplement
public interface Value {

  /**
   * @return identifier for the current option
   */
  String getId();

  /**
   * @return human readable name to use when displaying the option
   */
  String getDisplayName();

  /**
   * @return the child {@link Value values} that form a composed {@link Value}.
   */
  Set<Value> getChilds();

  /**
   * @return the name of the part which this {@link Value} is from
   */
  String getPartName();
}
