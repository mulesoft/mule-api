/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * <code>CustomNotificationListener</code> is an observer interface that can be used to listen for Custom notifications using
 * <code>MuleContext.fireCustomEvent(..)</code>. Custom notifications can be used by components and other objects such as routers,
 * transformers, agents, etc to communicate a change of state to each other.
 */
public interface CustomNotificationListener<T extends CustomNotification>
    extends NotificationListener<CustomNotification> {
  // no methods
}
