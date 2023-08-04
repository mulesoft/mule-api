/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.scheduler;

import org.mule.api.annotation.NoImplement;

import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.Executor;

/**
 * Parameters to use when building the {@link Executor}s for the scheduler service.
 * 
 * @since 1.0
 */
@NoImplement
public interface SchedulerPoolsConfig {

  /**
   * @return The {@link SchedulerPoolStrategy} to be used
   * @since 1.3.0
   */
  SchedulerPoolStrategy getSchedulerPoolStrategy();

  /**
   * @return the maximum time (in milliseconds) to wait until all tasks in all the runtime thread pools have completed execution
   *         when stopping the scheduler service.
   */
  OptionalLong getGracefulShutdownTimeout();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the number of threads to keep in the {@code cpu_lite} pool, even if they are idle.
   */
  OptionalInt getCpuLightPoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the size of the queue to use for holding {@code cpu_lite} tasks before they are executed.
   */
  OptionalInt getCpuLightQueueSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the number of threads to keep in the {@code I/O} pool.
   */
  OptionalInt getIoCorePoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the maximum number of threads to allow in the {@code I/O} pool.
   */
  OptionalInt getIoMaxPoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the size of the queue to use for holding {@code I/O} tasks before they are executed.
   */
  OptionalInt getIoQueueSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return when the number of threads in the {@code I/O} pool is greater than {@link #getIoCorePoolSize()}, this is the maximum
   *         time (in milliseconds) that excess idle threads will wait for new tasks before terminating.
   */
  OptionalLong getIoKeepAlive();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the number of threads to keep in the {@code cpu_intensive} pool, even if they are idle.
   */
  OptionalInt getCpuIntensivePoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#DEDICATED}
   *
   * @return the size of the queue to use for holding {@code cpu_intensive} tasks before they are executed.
   */
  OptionalInt getCpuIntensiveQueueSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#UBER}
   *
   * @return the number of threads to keep in the {@code UBER} pool.
   */
  OptionalInt getUberCorePoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#UBER}
   *
   * @return the maximum number of threads to allow in the {@code UBER} pool.
   */
  OptionalInt getUberMaxPoolSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#UBER}
   *
   * @return the size of the queue to use for holding {@code UBER} tasks before they are executed.
   */
  OptionalInt getUberQueueSize();

  /**
   * Only applies when {@link #getSchedulerPoolStrategy()} is {@link SchedulerPoolStrategy#UBER}
   *
   * @return when the number of threads in the {@code Uber} pool is greater than {@link #getUberCorePoolSize()}, this is the
   *         maximum time (in milliseconds) that excess idle threads will wait for new tasks before terminating.
   */
  OptionalLong getUberKeepAlive();

  /**
   * @return the prefix to prepend to the names of the threads of the pools created for a scheduler with this configuration.
   */
  String getThreadNamePrefix();
}
