/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

public interface MessageProcessorNotificationListener<T extends MessageProcessorNotification>
    extends NotificationListener<MessageProcessorNotification> {
}
