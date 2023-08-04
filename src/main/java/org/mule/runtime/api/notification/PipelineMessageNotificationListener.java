/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

public interface PipelineMessageNotificationListener<T extends PipelineMessageNotification>
    extends NotificationListener<PipelineMessageNotification> {
}
