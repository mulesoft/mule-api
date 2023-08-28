/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.source;

/**
 * Configuration interface for cron scheduler
 *
 * @since 1.0
 */
public interface CronSchedulerConfiguration extends SchedulerConfiguration {

  /**
   * @return the cron expression
   */
  String getExpression();

  /**
   * @return The ID of the time zone in which the expression will be based. Refer to {@code java.util.TimeZone} for the format and
   *         possible values of the timeZone ID.
   */
  String getTimeZone();

}
