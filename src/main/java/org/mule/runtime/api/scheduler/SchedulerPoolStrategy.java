/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
