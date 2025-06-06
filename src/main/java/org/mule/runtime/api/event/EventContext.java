/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.event;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.location.ComponentLocation;

import java.time.Instant;

@NoImplement
public interface EventContext {

  /**
   * Unique time-based id (UUID) for this {@link EventContext}.
   *
   * @return the UUID for this {@link EventContext}
   */
  String getId();

  /**
   * Unique time-based id (UUID) for this {@link EventContext}'s root.
   *
   * @return the UUID for this {@link EventContext}' root.
   */
  String getRootId();

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
   * @return a timestamp indicating when the message was received by the connector source.
   */
  Instant getReceivedTime();

  /**
   * @return a timestamp indicating when the execution of this context began.
   * @since 1.10, 1.9.7
   */
  default Instant getStartTime() {
    return Instant.MIN;
  }

  /**
   *
   * @return the location where this context's events come from
   */
  ComponentLocation getOriginatingLocation();

}
