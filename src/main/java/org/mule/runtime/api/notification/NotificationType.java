/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * Type of notification to fire.
 *
 * @since 1.1
 */
public enum NotificationType {

  /**
   * Provides information about processing.
   */
  INFO,
  /**
   * Provides tracking around processing.
   */
  TRACE,
  /**
   * Provides warnings about processing.
   */
  WARN,
  /**
   * Details errors during processing.
   */
  ERROR,
  /**
   * Details fatal errors during processing.
   */
  FATAL

}
