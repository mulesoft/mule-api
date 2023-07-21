/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
