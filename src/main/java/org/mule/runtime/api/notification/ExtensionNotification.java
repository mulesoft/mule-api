/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
