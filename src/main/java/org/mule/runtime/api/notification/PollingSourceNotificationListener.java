/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * <code>PollingSourceNotificationListener</code> is an observer interface that objects can use to receive notifications about the
 * state of polls in Polling Sources.
 */
public interface PollingSourceNotificationListener extends NotificationListener<PollingSourceNotification> {
}
