/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.component;

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

  String getFileName();

  int getLineInFile();

}
