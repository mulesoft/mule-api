/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

import java.util.Optional;

/**
 * Provides information about the location of a component within an application.
 *
 * @since 1.0
 */
public interface ComponentLocation {

  /**
   * @return the unique absolute path of the component in the application.
   */
  String getPath();

  /**
   * @return the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<String> getFileName();

  /**
   * @return the line number in the config file of the application where this component is defined, if it was defined in a config file.
   */
  Optional<Integer> getLineInFile();

}
