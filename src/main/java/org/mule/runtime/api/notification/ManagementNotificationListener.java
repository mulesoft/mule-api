/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>ManagementNotificationListener</code> is an observer interface that objects can use to receive notifications about the
 * state of the Mule instance and its resources
 */
public interface ManagementNotificationListener<T extends ManagementNotification>
    extends NotificationListener<ManagementNotification> {
  // no methods
}
