/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * Allows the Mule container, extensions and applications to fire {@link ServerNotification notifications}.
 * <p>
 * A {@link #fire(ServerNotification) fired} {@link ServerNotification notification} is dispatched by the implementation to any
 * registered listener for the concrete type of the {@link ServerNotification}.
 * 
 * @since 1.0
 */
public interface ServerNotificationFirer {

  /**
   * Send the {@code notification} to all the registered listeners.
   * 
   * @param notification the notification to fire.
   */
  void fire(ServerNotification notification);
}
