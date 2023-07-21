/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

public interface AsyncMessageNotificationListener<T extends AsyncMessageNotification>
    extends NotificationListener<AsyncMessageNotification> {
}
