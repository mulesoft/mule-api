/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta;

/**
 * Enumerates the different types of external libraries an extension may require
 *
 * @since 1.0
 */
public enum ExternalLibraryType {
  /**
   * Native libraries
   */
  NATIVE,

  /**
   * Jar files
   */
  JAR,

  /**
   * External dependencies
   */
  DEPENDENCY
}
