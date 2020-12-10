/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.api.config;

import org.mule.runtime.api.util.MuleSystemProperties;

/**
 * List of features that can be configured to be enabled per application by using.
 */
public enum Feature {

  /**
   * @deprecated Used just for testing.
   * @since 4.4.0, 4.3.1
   */
  TESTING_FEATURE("Feature used just for testing purposes.");

  private final String description;

  Feature(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
