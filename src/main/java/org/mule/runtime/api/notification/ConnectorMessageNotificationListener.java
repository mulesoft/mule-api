/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * Observer interface to receive notifications about messages being sent and received from connectors
 */
public interface ConnectorMessageNotificationListener<T extends ConnectorMessageNotification>
    extends NotificationListener<ConnectorMessageNotification> {
  // no methods
}
