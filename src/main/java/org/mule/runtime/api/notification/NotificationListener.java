/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * Observer interface that objects can implement and register themselves with the Mule Server to receive notifications when a
 * {@link Notification} is {@link NotificationDispatcher#dispatch(Notification) fired}.
 * 
 * @param <T> the concrete type of {@link Notification} that an implementation can handle.
 * @since 4.0
 */
public interface NotificationListener<T extends Notification> {

  /**
   * Allows the notification handler to perform otimizations on the handling of the {@link Notification} when
   * {@link NotificationDispatcher#dispatch(Notification) fired}.
   * 
   * @return true if this listener is expected to perform blocking I/O operations, false otherwise.
   */
  default boolean isBlocking() {
    return true;
  }

  /**
   * Handles the {@link NotificationDispatcher#dispatch(Notification) fired} {@link Notification}.
   * 
   * @param notification the {@link Notification} to handle.
   */
  void onNotification(T notification);
}
