/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.util;

/**
 * Constants for interaction with the underlying Java runtime
 *
 * @since 1.5.0
 */
public final class JavaConstants {

  /**
   * The version for Java 8
   */
  public static final String JAVA_VERSION_8 = "1.8";

  /**
   * The version for Java 11
   */
  public static final String JAVA_VERSION_11 = "11";

  /**
   * The version for Java 17
   */
  public static final String JAVA_VERSION_17 = "17";

  private JavaConstants() {}
}
