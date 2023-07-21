/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * Allows the Mule container, extensions and applications to fire {@link Notification notifications}.
 * <p>
 * A {@link #dispatch(Notification) fired} {@link Notification notification} is dispatched by the implementation to any registered
 * listener for the concrete type of the {@link Notification}.
 * 
 * @since 4.0
 */
public interface NotificationDispatcher {

  /**
   * Send the {@code notification} to all the registered listeners.
   * 
   * @param notification the notification to fire.
   */
  void dispatch(Notification notification);
}
