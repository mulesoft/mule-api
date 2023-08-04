/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
