/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>RoutingNotificationListener</code> is an observer interface that objects can use to receive notifications about routing
 * events such as async-reply misses.
 */
public interface RoutingNotificationListener<T extends RoutingNotification>
    extends NotificationListener<RoutingNotification> {
  // no methods
}
