/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
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
   * @see Scheduler#isShutdown()
   */
  boolean isShutdown();

  /**
   * @see Scheduler#isTerminated()
   */
  boolean isTerminated();

}
