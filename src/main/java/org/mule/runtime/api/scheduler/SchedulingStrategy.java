/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.scheduler;

import org.mule.api.annotation.NoImplement;

import java.util.concurrent.ScheduledFuture;

/**
 * Strategy used by the {@link org.mule.runtime.api.scheduler.Scheduler} for executing jobs.
 *
 * @since 4.4
 */
@NoImplement
public interface SchedulingStrategy {

  /**
   * Schedules a job.
   *
   * @param executor the corresponding {@link org.mule.runtime.api.scheduler.Scheduler} instance.
   * @param job      The {@link Runnable} job that has to be executed.
   * @return the newly scheduled job.
   * @throws NullPointerException In case the scheduled job handler is null.
   */
  ScheduledFuture<?> schedule(org.mule.runtime.api.scheduler.Scheduler executor, Runnable job);
}
