/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.component.Component;

/**
 * This notification type includes information about the event, exception and flow where it occurred.
 */
public abstract class EnrichedServerNotification extends AbstractServerNotification {

  protected EnrichedNotificationInfo notificationInfo;

  public EnrichedServerNotification(EnrichedNotificationInfo notificationInfo, int action, String resourceIdentifier) {
    super(notificationInfo, action, resourceIdentifier);
    this.notificationInfo = notificationInfo;
  }

  public EnrichedServerNotification(EnrichedNotificationInfo notificationInfo, int action, ComponentLocation componentLocation) {
    this(notificationInfo, action, componentLocation != null ? componentLocation.getRootContainerName() : null);
  }

  /**
   * This function should not be used anymore, try getEvent, getComponent or getException depending on the situation.
   *
   * @return the notification information instead of the event used before
   */
  @Deprecated
  @Override
  public Object getSource() {
    return notificationInfo;
  }

  public Event getEvent() {
    return notificationInfo.getEvent();
  }

  public Component getComponent() {
    return notificationInfo.getComponent();
  }

  public Exception getException() {
    return notificationInfo.getException();
  }

  @Override
  public String toString() {
    return getEventName() + "{" + "action=" + getActionName(action) + ", resourceId=" + resourceIdentifier
        + ", serverId=" + serverId + ", timestamp=" + timestamp + "}";
  }

  public String getLocationUri() {
    if (getComponent() == null) {
      return null;
    }

    ComponentLocation location = getComponent().getLocation();
    return location.getRootContainerName() + "/" + location.getComponentIdentifier().getIdentifier().toString();
  }

  public EnrichedNotificationInfo getInfo() {
    return notificationInfo;
  }
}
