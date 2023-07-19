/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

public interface ErrorHandlerNotificationListener<T extends ErrorHandlerNotification>
    extends NotificationListener<ErrorHandlerNotification> {
}
