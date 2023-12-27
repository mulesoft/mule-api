/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.scheduler;

import static java.lang.String.format;
import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.MIN_PRIORITY;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.mule.runtime.api.artifact.Registry;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Provides a fluent way of customizing a {@link Scheduler} obtained through the {@link SchedulerService}.
 *
 * @since 1.0
 */
public class SchedulerConfig {

  /**
   * Whenever possible, use a {@link SchedulerConfig} from the {@link Registry} or inject it rather than building a new one with
   * this method. {@link SchedulerConfig}s obtained that way are already configured with base configuration based on the artifact
   * it belongs to.
   *
   * @return a default configuration, which can be further customized.
   */
  public static SchedulerConfig config() {
    return new SchedulerConfig();
  }

  private final Integer maxConcurrentTasks;
  private final String schedulerPrefix;
  private final String schedulerName;
  private final Optional<Boolean> waitAllowed;
  private final Optional<Boolean> runCpuLightWhenTargetBusy;
  private final Supplier<Long> shutdownTimeoutMillis;
  private final Optional<Integer> priority;

  private SchedulerConfig() {
    this.maxConcurrentTasks = null;
    this.schedulerPrefix = null;
    this.schedulerName = null;
    this.waitAllowed = empty();
    this.runCpuLightWhenTargetBusy = empty();
    this.shutdownTimeoutMillis = () -> null;
    this.priority = empty();
  }

  private SchedulerConfig(Integer maxConcurrentTasks, String schedulerPrefix, String schedulerName,
                          Optional<Boolean> waitAllowed, Optional<Boolean> runCpuLightWhenTargetBusy,
                          Supplier<Long> shutdownTimeoutMillis, Optional<Integer> priority) {
    this.maxConcurrentTasks = maxConcurrentTasks;
    this.schedulerPrefix = schedulerPrefix;
    this.schedulerName = schedulerName;
    this.waitAllowed = waitAllowed;
    this.runCpuLightWhenTargetBusy = runCpuLightWhenTargetBusy;
    this.shutdownTimeoutMillis = shutdownTimeoutMillis;
    this.priority = priority;
  }

  /**
   * Sets the max tasks that can be run at the same time for the target {@link Scheduler}.
   * <p>
   * This is useful to apply throttling on the target {@link Scheduler}. The way exceeding tasks will be handled is determined by
   * the target {@link Scheduler}.
   *
   * @param maxConcurrentTasks how many tasks can be running at the same time for the target {@link Scheduler}.
   * @return the updated configuration.
   */
  public SchedulerConfig withMaxConcurrentTasks(int maxConcurrentTasks) {
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, runCpuLightWhenTargetBusy,
                               shutdownTimeoutMillis, priority);
  }

  /**
   * @return how many tasks can be running at the same time for the target {@link Scheduler}.
   */
  public Integer getMaxConcurrentTasks() {
    return maxConcurrentTasks;
  }

  /**
   * Sets the prefix to prepend to the name for the target {@link Scheduler}, which will override the default one.
   *
   * @param schedulerPrefix the prefix for the name for the target {@link Scheduler}.
   * @return the updated configuration.
   */
  public SchedulerConfig withPrefix(String schedulerPrefix) {
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, runCpuLightWhenTargetBusy,
                               shutdownTimeoutMillis, priority);
  }

  /**
   * Sets the name for the target {@link Scheduler}, which will override the default one.
   *
   * @param schedulerName the name for the target {@link Scheduler}.
   * @return the updated configuration.
   */
  public SchedulerConfig withName(String schedulerName) {
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, runCpuLightWhenTargetBusy,
                               shutdownTimeoutMillis, priority);
  }

  /**
   * @return the name for the target {@link Scheduler}.
   */
  public String getSchedulerName() {
    return schedulerPrefix == null ? schedulerName : format("[%s].%s", schedulerPrefix, schedulerName);
  }

  /**
   * @return {@code true} if {@link #withName(String)} was called with a non-null value, {@code false} otherwise.
   */
  public boolean hasName() {
    return schedulerName != null;
  }

  /**
   * When {@code true}, if the target custom {@link Scheduler} tries to dispatch a task to the {@code cpu-light} {@link Scheduler}
   * when it's busy, the task will be run by the target {@link Scheduler} instead of dispatching it to cpu-lite.
   * <p>
   * This is only applicable for <b>custom</b> {@link Scheduler}s. This behaviour cannot be changed for the runtime managed
   * {@link Scheduler}.
   * <p>
   * A custom scheduler may have flags set for both {@code runCpuLightWhenTargetBusy} and {@code waitAllowed}. In such case, the
   * behaviour when the target {@link Scheduler} is busy depends on the type of the target {@link Scheduler}: if it's cpu-light or
   * the same as the current, the task will run directly, otherwise it will wait.
   *
   * @return the updated configuration
   * @since 1.1
   */
  public SchedulerConfig withDirectRunCpuLightWhenTargetBusy(boolean runCpuLightWhenTargetBusy) {
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, of(runCpuLightWhenTargetBusy),
                               shutdownTimeoutMillis, priority);
  }

  /**
   * @return When {@code true}, if the target custom {@link Scheduler} tries to dispatch a task to the {@code cpu-light}
   *         {@link Scheduler} when it's busy, the task will be run by the target {@link Scheduler} instead of dispatching it to
   *         cpu-lite.
   * @since 1.1
   */
  public Optional<Boolean> getDirectRunCpuLightWhenTargetBusy() {
    return runCpuLightWhenTargetBusy;
  }

  /**
   * Whether the threads of the target custom {@link Scheduler} may block to wait when dispatching to a busy {@link Scheduler}.
   * <p>
   * This is only applicable for <b>custom</b> {@link Scheduler}s. This behaviour cannot be changed for the runtime managed
   * {@link Scheduler}.
   * <p>
   * A custom scheduler may have flags set for both {@code runCpuLightWhenTargetBusy} and {@code waitAllowed}. In such case, the
   * behaviour when the target {@link Scheduler} is busy depends on the type of the target {@link Scheduler}: if it's cpu-light or
   * the same as the current, the task will run directly, otherwise it will wait.
   *
   * @return the updated configuration
   */
  public SchedulerConfig withWaitAllowed(boolean waitAllowed) {
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, of(waitAllowed), runCpuLightWhenTargetBusy,
                               shutdownTimeoutMillis, priority);
  }

  /**
   * @return whether the threads of the target custom {@link Scheduler} may block to wait when dispatching to a busy
   *         {@link Scheduler}.
   */
  public Optional<Boolean> getWaitAllowed() {
    return waitAllowed;
  }

  /**
   * Sets the graceful shutdown timeout to use when stopping the target {@link Scheduler}.
   *
   * @param shutdownTimeoutSupplier a supplier of the value of the timeout to use when gracefully stopping the target
   *                                {@link Scheduler}, expressed in the provided {@link TimeUnit}.
   * @param shutdownTimeoutUnit     the unit of the timeout to use when gracefully stopping the target {@link Scheduler}.
   * @return the updated configuration
   */
  public SchedulerConfig withShutdownTimeout(Supplier<Long> shutdownTimeoutSupplier, TimeUnit shutdownTimeoutUnit) {
    requireNonNull(shutdownTimeoutUnit);

    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, runCpuLightWhenTargetBusy, () -> {
      long shutdownTimeout = shutdownTimeoutSupplier.get();
      validateTimeoutValue(shutdownTimeout);
      return shutdownTimeoutUnit.toMillis(shutdownTimeout);
    }, priority);
  }

  /**
   * Sets the graceful shutdown timeout to use when stopping the target {@link Scheduler}.
   *
   * @param shutdownTimeout     the value of the timeout to use when gracefully stopping the target {@link Scheduler}, expressed
   *                            in the provided {@link TimeUnit}.
   * @param shutdownTimeoutUnit the unit of the timeout to use when gracefully stopping the target {@link Scheduler}.
   * @return the updated configuration
   */
  public SchedulerConfig withShutdownTimeout(long shutdownTimeout, TimeUnit shutdownTimeoutUnit) {
    requireNonNull(shutdownTimeoutUnit);
    validateTimeoutValue(shutdownTimeout);
    return withShutdownTimeout(() -> shutdownTimeout, shutdownTimeoutUnit);
  }

  private void validateTimeoutValue(long shutdownTimeout) {
    if (shutdownTimeout < 0) {
      throw new IllegalArgumentException(format("'shutdownTimeout' must be a possitive long. %d passed", shutdownTimeout));
    }
  }

  /**
   * @return a supplier of the timeout to use when gracefully stopping the target {@link Scheduler}, in millis.
   */
  public Supplier<Long> getShutdownTimeoutMillis() {
    return shutdownTimeoutMillis;
  }

  /**
   * Sets the priority for threads generated by the underlying {@link java.util.concurrent.ExecutorService}.
   *
   * @param priority the desired priority.
   * @return the updated configuration.
   * @throws IllegalArgumentException If the priority is not in the range {@link Thread#MIN_PRIORITY} to
   *                                  {@link Thread#MAX_PRIORITY}.
   */
  public SchedulerConfig withPriority(int priority) {
    if (priority < MIN_PRIORITY || priority > MAX_PRIORITY) {
      throw new IllegalArgumentException(format("'priority' must be in the range [%d, %d]. %d passed",
                                                MIN_PRIORITY,
                                                MAX_PRIORITY,
                                                priority));
    }
    return new SchedulerConfig(maxConcurrentTasks, schedulerPrefix, schedulerName, waitAllowed, runCpuLightWhenTargetBusy,
                               shutdownTimeoutMillis, of(priority));
  }

  /**
   * @return the selected priority or {@link Optional#empty} if none was specified.
   */
  public Optional<Integer> getPriority() {
    return priority;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SchedulerConfig that = (SchedulerConfig) o;

    if (maxConcurrentTasks != null ? !maxConcurrentTasks.equals(that.maxConcurrentTasks) : that.maxConcurrentTasks != null) {
      return false;
    }
    if (schedulerPrefix != null ? !schedulerPrefix.equals(that.schedulerPrefix) : that.schedulerPrefix != null) {
      return false;
    }
    if (schedulerName != null ? !schedulerName.equals(that.schedulerName) : that.schedulerName != null) {
      return false;
    }
    if (waitAllowed != null ? !waitAllowed.equals(that.waitAllowed) : that.waitAllowed != null) {
      return false;
    }
    if (!priority.equals(that.priority)) {
      return false;
    }

    return shutdownTimeoutMillis != null ? shutdownTimeoutMillis.equals(that.shutdownTimeoutMillis)
        : that.shutdownTimeoutMillis == null;
  }

  @Override
  public int hashCode() {
    int result = maxConcurrentTasks != null ? maxConcurrentTasks.hashCode() : 0;
    result = 31 * result + (schedulerPrefix != null ? schedulerPrefix.hashCode() : 0);
    result = 31 * result + (schedulerName != null ? schedulerName.hashCode() : 0);
    result = 31 * result + (waitAllowed != null ? waitAllowed.hashCode() : 0);
    result = 31 * result + (shutdownTimeoutMillis != null ? shutdownTimeoutMillis.hashCode() : 0);
    result = 31 * result + priority.hashCode();
    return result;
  }
}
