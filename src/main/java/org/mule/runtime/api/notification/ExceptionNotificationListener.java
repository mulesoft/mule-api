/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>ExceptionNotificationListener</code> is an observer interface that objects can implement and then register themselves
 * with the Mule manager to be notified when a Exception event occurs.
 */
public interface ExceptionNotificationListener extends NotificationListener<ExceptionNotification> {
  // no methods
}
