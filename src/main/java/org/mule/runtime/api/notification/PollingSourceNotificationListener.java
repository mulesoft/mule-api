/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>PollingSourceNotificationListener</code> is an observer interface that objects can use to receive notifications about the
 * state of polls in Polling Sources.
 */
public interface PollingSourceNotificationListener extends NotificationListener<PollingSourceNotification> {
}
