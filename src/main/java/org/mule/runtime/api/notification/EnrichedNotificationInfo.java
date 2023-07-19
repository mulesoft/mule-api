/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.event.Event;

/**
 * This class contains information about an event/exception, to be used in notifications without directly exposing it.
 */
public final class EnrichedNotificationInfo {

  private Event event;
  private Component component;
  private Exception exception;

  /**
   * Extract information from the event and exception to provide notification data.
   *
   * @param event     the event to extract information from
   * @param e         the exception that occurred
   * @param component the component (processor, source, etc) that triggered the notification
   * @return
   */
  public static EnrichedNotificationInfo createInfo(Event event, Exception e, Component component) {
    return new EnrichedNotificationInfo(event, component, e);
  }

  public EnrichedNotificationInfo(Event event, Component component, Exception exception) {
    this.event = event;
    this.component = component;
    this.exception = exception;
  }

  public org.mule.runtime.api.event.Event getEvent() {
    return event;
  }

  public Component getComponent() {
    return component;
  }

  public Exception getException() {
    return exception;
  }

}
