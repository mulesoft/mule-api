/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * Observer interface that objects can implement and register themselves with the Mule Server to receive notifications when a
 * {@link ServerNotification} is {@link ServerNotificationFirer#fire(ServerNotification) fired}.
 * 
 * @param <T> the concrete type of {@link ServerNotification} that an implementation can handle.
 * @since 1.0
 */
public interface ServerNotificationListener<T extends ServerNotification> {

  /**
   * Allows the notification handler to perform otimizations on the handling of the {@link ServerNotification} when
   * {@link ServerNotificationFirer#fire(ServerNotification) fired}.
   * 
   * @return true if this listener is expected to perform blocking I/O operations, false otherwise.
   */
  default boolean isBlocking() {
    return true;
  }

  /**
   * Handles the {@link ServerNotificationFirer#fire(ServerNotification) fired} {@link ServerNotification}.
   * 
   * @param notification the {@link ServerNotification} to handle.
   */
  void onNotification(T notification);
}
