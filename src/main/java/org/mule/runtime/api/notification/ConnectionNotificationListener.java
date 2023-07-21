/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>ConnectionNotificationListener</code> is an observer interface that objects can implement and then register themselves
 * with the Mule manager to be notified when a Connection event occurs.
 */
public interface ConnectionNotificationListener<T extends ConnectionNotification>
    extends NotificationListener<ConnectionNotification> {
  // no methods
}
