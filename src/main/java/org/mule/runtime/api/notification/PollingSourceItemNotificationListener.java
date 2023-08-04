/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>PollingSourceNotificationListener</code> is an observer interface that objects can use to receive notifications about the
 * state items polled in Polling Sources.
 */
public interface PollingSourceItemNotificationListener extends NotificationListener<PollingSourceItemNotification> {
}
