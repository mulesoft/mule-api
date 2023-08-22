/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.component.location.ComponentLocation;

public final class ErrorHandlerNotification extends EnrichedServerNotification {

  // Fired when processing of exception strategy starts
  public static final int PROCESS_START = EXCEPTION_STRATEGY_MESSAGE_EVENT_ACTION_START_RANGE + 1;
  // Fired when processing of exception strategy ends
  public static final int PROCESS_END = EXCEPTION_STRATEGY_MESSAGE_EVENT_ACTION_START_RANGE + 2;

  static {
    registerAction("exception strategy process start", PROCESS_START);
    registerAction("exception strategy process end", PROCESS_END);
  }

  public ErrorHandlerNotification(EnrichedNotificationInfo notificationInfo, ComponentLocation componentLocation, int action) {
    super(notificationInfo, action, componentLocation);
  }

  @Override
  public String getEventName() {
    return "ErrorHandlerNotification";
  }
}
