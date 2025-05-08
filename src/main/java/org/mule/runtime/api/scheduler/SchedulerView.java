/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
   * 
   * @return
   * @since 1.10
   */
  default String getActualExecutorId() {
    return "";
  }

  /**
   * 
   * @return
   * @since 1.10
   */
  default String getActualExecutorToString() {
    return "";
  }

  /**
   * 
   * @return
   * @since 1.10
   */
  default String getThreadType() {
    return "";
  }

  /**
   * @see Scheduler#isShutdown()
   */
  boolean isShutdown();

  /**
   * @see Scheduler#isTerminated()
   */
  boolean isTerminated();

}
