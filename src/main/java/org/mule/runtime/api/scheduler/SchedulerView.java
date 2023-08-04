/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.scheduler;

/**
 * Provides a read-only view of the state of a {@link Scheduler}.
 *
 * @since 1.0
 */
public interface SchedulerView {

  /**
   * Returns a name that indicates where was this scheduler created.
   *
   * @return the name of this scheduler.
   */
  String getName();

  /**
   * @see Scheduler#isShutdown()
   */
  boolean isShutdown();

  /**
   * @see Scheduler#isTerminated()
   */
  boolean isTerminated();

}
