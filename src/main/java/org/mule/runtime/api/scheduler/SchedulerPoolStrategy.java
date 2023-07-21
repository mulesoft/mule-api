/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.scheduler;

/**
 * The strategy that the {@link SchedulerService} should use to provision the thread pools that back the three main types of
 * schedulers
 *
 * @since 1.3.0
 */
public enum SchedulerPoolStrategy {

  /**
   * The three scheduler types are backed by one unique thread pool.
   */
  UBER,

  /**
   * Each scheduler type is backed by its own thread pool
   */
  DEDICATED
}
