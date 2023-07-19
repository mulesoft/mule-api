/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * Observer interface to receive notifications about messages being processed by policies
 */
public interface PolicyNotificationListener<T extends PolicyNotification> extends NotificationListener<PolicyNotification> {

}
