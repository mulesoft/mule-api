/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>ExtensionNotificationListener</code> is an observer interface that objects can implement and then register themselves
 * with the Mule manager to be notified when an Extension event occurs.
 */
public interface ExtensionNotificationListener extends NotificationListener<ExtensionNotification> {

}
