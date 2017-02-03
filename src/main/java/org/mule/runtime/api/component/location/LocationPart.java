/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component.location;

import org.mule.runtime.api.component.TypedComponentIdentifier;

import java.util.Optional;

/**
 * A location part represent an specific location of a component within another component.
 *
 * @since 1.0
 */
public interface LocationPart {

  /**
   * @return the string representation of the part
   */
  String getPartPath();

  /**
   * A location part represent an specific location of a component within another component.
   *
   * @since 4.0
   */
  Optional<TypedComponentIdentifier> getPartIdentifier();

  /**
   * @return the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<String> getFileName();

  /**
   * @return the line number in the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<Integer> getLineInFile();

}
