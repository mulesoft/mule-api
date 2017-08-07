/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.event;

import org.mule.runtime.api.component.location.ComponentLocation;

import java.time.OffsetTime;
import java.util.Optional;

public interface EventContext {

  /**
   * Unique time-based id (UUID) for this {@link EventContext}.
   *
   * @return the UUID for this {@link EventContext}
   */
  String getId();

  /**
   * The correlation ID is used to correlate messages between different flows and systems.
   * <p>
   * If the connector that receives the source message supports the concept of a correlation ID then the connector should create
   * an instance of {@link EventContext} using this value. If on the other hand, no correlation ID is received by the source
   * connector then a time-based UUID, also available via {@link #getId()} is used.
   *
   * @return the correlation id.
   */
  String getCorrelationId();

  /**
   * @return a timestamp indicating when the message was received by the connector source
   */
  OffsetTime getReceivedTime();

  /**
   *
   * @return the location where this context's events come from
   */
  ComponentLocation getOriginatingLocation();

  /**
   * Returns {@code this} context's parent if it has one
   *
   * @return {@code this} context's parent or {@link Optional#empty()} if it doesn't have one
   */
  Optional<EventContext> getParentContext();

}
