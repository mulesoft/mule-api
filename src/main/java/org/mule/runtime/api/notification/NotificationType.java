/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
