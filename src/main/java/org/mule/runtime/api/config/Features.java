/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

/**
 * List of {@link Feature} that can be configured to be enabled or disabled per application depending on the deployment context.
 * 
 * @since 4.4.0 4.3.1
 */
public enum Features implements Feature {
  ;

  private final String description;

  Features(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
