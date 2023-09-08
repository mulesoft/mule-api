/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.scheduler;

import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * Allows tasks to be submitted/scheduled to a specific executor in the Mule runtime. Different {@link Scheduler} instances may be
 * backed by the same {@link ExecutorService}, allowing for a fine control of the source of the tasks that the underlying
 * {@link ExecutorService} will run.
 * <p>
 * See {@link ScheduledExecutorService} and {@link ExecutorService} for documentation on the provided methods.
 *
 * @since 1.0
 */
public interface Scheduler extends ScheduledExecutorService {

  /**
   * Creates and executes a periodic action that becomes enabled according to given {@code cronExpression} and {@code timeZone}.
   * If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the task will only
   * terminate via cancellation or termination of the executor. If any execution of this task takes longer than the time before
   * the next execution should start, then subsequent executions may start late, but will not concurrently execute.
   *
   * @param command        the task to execute
   * @param cronExpression the
   *                       <a href="http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html">cron</a>
   *                       expression string to base the schedule on.
   * @return a ScheduledFuture representing pending completion of the task, and whose {@code get()} method will throw an exception
   *         upon cancellation
   * @throws RejectedExecutionException if the task cannot be scheduled for execution
   * @throws NullPointerException       if command is null
   */
  ScheduledFuture<?> scheduleWithCronExpression(Runnable command, String cronExpression);

  /**
   * Creates and executes a periodic action that becomes enabled according to given {@code cronExpression} in the given
   * {@code timeZone}. If any execution of the task encounters an exception, subsequent executions are suppressed. Otherwise, the
   * task will only terminate via cancellation or termination of the executor. If any execution of this task takes longer than the
   * time before the next execution should start, then subsequent executions may start late, but will not concurrently execute.
   *
   * @param command        the task to execute
   * @param cronExpression the
   *                       <a href="http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html">cron</a>
   *                       expression string to base the schedule on.
   * @param timeZone       the time-zone for the schedule.
   * @return a ScheduledFuture representing pending completion of the task, and whose {@code get()} method will throw an exception
   *         upon cancellation
   * @throws RejectedExecutionException if the task cannot be scheduled for execution
   * @throws NullPointerException       if command is null
   */
  ScheduledFuture<?> scheduleWithCronExpression(Runnable command, String cronExpression, TimeZone timeZone);

  /**
   * Tries to do a graceful shutdown.
   * <p>
   * If this hasn't terminated after a time configured on instantiation, a forceful shutdown takes place.
   */
  void stop();

  /**
   * Returns a name that indicates where was this scheduler created.
   *
   * @return the name of this scheduler.
   */
  String getName();

  /**
   * @return the string to append to the name of the thread that executes a task for this Scheduler, or {@code null} if nothing is
   *         to be appended.
   * 
   * @since 1.1
   */
  default String getThreadNameSuffix() {
    return getName();
  }
}
