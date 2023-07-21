/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.notification;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.model.ComponentModel;

/**
 * A model which represents a notification that could be fired by the component represented by the {@link ComponentModel}.
 *
 * @since 1.1
 */
@NoImplement
public interface NotificationModel {

  /**
   * Gets the namespace of notification. Represents the origin of who fires this notification, so it could be the namespace of an
   * extension or the {@code MULE} namespace.
   *
   * @return The namespace of the error
   */
  String getNamespace();

  /**
   * Returns the ID of the notification.
   *
   * @return the identifier for the notification
   */
  String getIdentifier();

  /**
   * Returns the {@link MetadataType} of the data associated to the notification.
   *
   * @return a not {@code null} {@link MetadataType}
   */
  MetadataType getType();

}
