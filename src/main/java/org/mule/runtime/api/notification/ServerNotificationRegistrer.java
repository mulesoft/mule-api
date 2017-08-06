/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import java.util.function.Function;

/**
 * Allows to register/unregister {@link ServerNotificationListener}s for {@link ServerNotification}s fired by the Mule container,
 * extensions and applications.
 * 
 * @since 1.0
 */
public interface ServerNotificationRegistrer {

  /**
   * Registers a {@link ServerNotificationListener}. The listener will be notified when a particular event happens within the
   * server. Typically this is not an event in the same sense as a Mule Event (although there is nothing stopping the
   * implementation of this class triggering listeners when a Mule Event is received).
   *
   * @param listener the listener to register
   */
  void registerListener(ServerNotificationListener listener);

  /**
   * Registers a {@link ServerNotificationListener}. The listener will be notified when a particular event happens within the
   * server. Typically this is not an event in the same sense as a Mule Event (although there is nothing stopping the
   * implementation of this class triggering listeners when a Mule Event is received).
   *
   * @param listener the listener to register
   * @param subscription a filter to apply on a fired {@link ServerNotification} before calling the {@code listener} with it.
   *        Non-null.
   */
  <T extends ServerNotification> void registerListener(ServerNotificationListener<T> listener, Function<T, Boolean> subscription);

  /**
   * Unregisters a previously registered {@link ServerNotificationListener}. If the listener has not already been registered, this
   * method should return without exception
   *
   * @param listener the listener to unregister
   */
  void unregisterListener(ServerNotificationListener listener);

}
