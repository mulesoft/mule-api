/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * <code>TransactionNotificationListener</code> is an observer interface that objects can implement and then register themselves
 * with the Mule manager to be notified when a Transaction event occurs.
 */
public interface TransactionNotificationListener<T extends TransactionNotification>
    extends NotificationListener<TransactionNotification> {
  // no methods
}
