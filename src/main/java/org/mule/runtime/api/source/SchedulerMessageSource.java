/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.source;

import static org.mule.runtime.api.component.ComponentIdentifier.buildFromStringRepresentation;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;

/**
 * Scheduler message source interface.
 * <p>
 * A scheduler source can be stopped or started at any time. Calling stop while the scheduler is stopped or calling start while
 * the scheduler is started won't have any effect. Method {@code isStarted} can be used to know if the scheduler message source is
 * running or not.
 * <p>
 * A scheduler can be manually triggered using the {@code trigger} method. Clients of this interface that want to redefine the
 * scheduling of the flow execution can stop the scheduler message source and call the {@code trigger} method whenever the want.
 * <p>
 * Calling {@code stop} method over a source will not affect any ongoing transaction.
 *
 * @since 4.0
 */
@NoImplement
public interface SchedulerMessageSource extends Stoppable, Startable {

  /**
   * {@link ComponentIdentifier} of an scheduler message source.
   */
  ComponentIdentifier SCHEDULER_MESSAGE_SOURCE_IDENTIFIER = buildFromStringRepresentation("mule:scheduler");

  /**
   * Triggers the execution of the flow where the scheduler message source is located.
   */
  void trigger();

  /**
   * @return true if the scheduler message source is running which means that the configured scheduling will run the flow
   *         according to it's configuration, false if the scheduler is not running.
   */
  boolean isStarted();

  /**
   * @return the source scheduler configuration.
   */
  SchedulerConfiguration getConfiguration();

}
