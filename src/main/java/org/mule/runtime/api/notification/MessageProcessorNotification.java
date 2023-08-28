/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import static org.mule.runtime.api.notification.EnrichedNotificationInfo.createInfo;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.event.EventContext;

public final class MessageProcessorNotification extends EnrichedServerNotification {

  private static final long serialVersionUID = 1L;

  public static final int MESSAGE_PROCESSOR_PRE_INVOKE = MESSAGE_PROCESSOR_EVENT_ACTION_START_RANGE + 1;
  public static final int MESSAGE_PROCESSOR_POST_INVOKE = MESSAGE_PROCESSOR_EVENT_ACTION_START_RANGE + 2;

  static {
    registerAction("message processor pre invoke", MESSAGE_PROCESSOR_PRE_INVOKE);
    registerAction("message processor post invoke", MESSAGE_PROCESSOR_POST_INVOKE);
  }

  private EventContext eventContext;

  public MessageProcessorNotification(EnrichedNotificationInfo notificationInfo, ComponentLocation componentLocation,
                                      EventContext eventContext, int action) {
    super(notificationInfo, action, componentLocation != null ? componentLocation.getRootContainerName() : null);
    this.eventContext = eventContext;
  }

  public static MessageProcessorNotification createFrom(Event event, ComponentLocation componentLocation,
                                                        Component processor, Exception exceptionThrown,
                                                        int action) {
    EnrichedNotificationInfo notificationInfo = createInfo(event, exceptionThrown, processor);
    return new MessageProcessorNotification(notificationInfo, componentLocation, (event.getContext()),
                                            action);
  }

  public EventContext getEventContext() {
    return eventContext;
  }

  @Override
  public String toString() {
    return getEventName() + "{" + "action=" + getActionName(action) + ", processor="
        + getComponent().getLocation().getLocation()
        + ", resourceId="
        + resourceIdentifier + ", serverId=" + serverId + ", timestamp=" + timestamp + "}";
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }

  @Override
  public String getEventName() {
    return "MessageProcessorNotification";
  }
}
