/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.error;

/**
 * Utility class to list all the commons error types that Mule and the components inside of it
 * could throw.
 *
 * @since 4.0
 */
public final class Errors {

  /**
   * Indicates that a problem occurred when transforming a value
   */
  public static final String TRANSFORMATION = "TRANSFORMATION";

  /**
   * Indicates that a problem occurred when resolving an expression
   */
  public static final String EXPRESSION = "EXPRESSION";

  /**
   * Indicate that the retry policy, of a certain component, to execute some action, eg: connectivity, delivery has
   * been exhausted
   */
  public static final String REDELIVERY_EXHAUSTED = "REDELIVERY_EXHAUSTED";

  /**
   * Indicates that the retrial of a certain execution block has been exhausted
   */
  public static final String RETRY_EXHAUSTED = "RETRY_EXHAUSTED";

  /**
   * Indicates that a problem occurred when routing a message
   */
  public static final String ROUTING = "ROUTING";

  /**
   * Indicates that was a problem and could not establish a connection
   */
  public static final String CONNECTIVITY = "CONNECTIVITY";

  /**
   * Indicates a problem of security type, eg: invalid credentials, expired token, etc.
   */
  public static final String SECURITY = "SECURITY";

  /**
   * Indicates that an unknown and unexpected error occurred
   */
  public static final String UNKNOWN = "UNKNOWN";

  /**
   * Indicates that a critical and severe error occurred
   */
  public static final String CRITICAL = "CRITICAL";

  /**
   * Wild card that matches with any error
   */
  public static final String ANY = "ANY";
}
