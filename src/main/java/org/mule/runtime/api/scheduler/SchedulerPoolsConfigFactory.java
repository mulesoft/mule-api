/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
