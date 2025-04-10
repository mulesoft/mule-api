/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.scheduler;

import java.util.concurrent.RejectedExecutionException;

/**
 * Exception thrown by a {@link Scheduler} when all of its threads are busy and it cannot accept a new task for execution.
 *
 * @since 1.0
 */
public final class SchedulerBusyException extends RejectedExecutionException {

  private static final long serialVersionUID = 9047649377760686741L;

  /**
   * Constructs a new exception with the specified message.
   *
   * @param message the detail message
   */
  public SchedulerBusyException(String message) {
    super(message);
  }

}

