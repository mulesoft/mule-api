/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.scheduler;

import java.util.Optional;

/**
 * Provides an instance of {@link SchedulerPoolsConfig} to use when building the infrastructure for the {@link Scheduler}s.
 * 
 * @since 1.0
 */
public interface SchedulerPoolsConfigFactory {

  /**
   * @return the {@link SchedulerPoolsConfig} to use, or {@link Optional#empty()} to indicate that it is the responsability of the
   *         caller to provide the configuration.
   */
  Optional<SchedulerPoolsConfig> getConfig();

}
