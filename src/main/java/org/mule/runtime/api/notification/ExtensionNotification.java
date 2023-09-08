/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.component.Component;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.metadata.TypedValue;

import java.io.Serializable;

/**
 * Notification fired by extensions, including custom data.
 *
 * @since 1.1
 */
public interface ExtensionNotification extends Notification, Serializable {

  /**
   * The {@link Event} being processed when the notification was fired.
   *
   * @return the associated event
   */
  Event getEvent();

  /**
   * The {@link Component} that fired the notification.
   *
   * @return the firing component
   */
  Component getComponent();

  /**
   * The custom {@link TypedValue} data associated to the notification.
   *
   * @return the data
   */
  TypedValue<?> getData();

}
