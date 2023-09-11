/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.source;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for fixed frequency scheduler
 * 
 * @since 1.0
 */
public interface FixedFrequencySchedulerConfiguration extends SchedulerConfiguration {

  /**
   * @return the {@link TimeUnit} of the scheduler
   */
  TimeUnit getTimeUnit();

  /**
   * @return the frequency of the scheduler in timeUnit
   */
  long getFrequency();

  /**
   * @return the time in timeUnit that it has to wait before executing the first task
   */
  long getStartDelay();

}
