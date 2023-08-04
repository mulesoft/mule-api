/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>MuleContextNotificationListener</code> is an observer interface that objects can implement to receive notifications about
 * secure access requests.
 */
public interface SecurityNotificationListener<T extends SecurityNotification>
    extends NotificationListener<SecurityNotification> {
  // no methods
}
