/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.notification;

import org.mule.api.annotation.NoImplement;

import java.util.Set;

/**
 * Indicates that the current model can declare which {@link NotificationModel} it fires.
 *
 * @since 1.1
 */
@NoImplement
public interface HasNotifications {

  /**
   * @return a {@link Set} of {@link NotificationModel} with the notifications that the current component fires.
   *
   * @see NotificationModel
   */
  Set<NotificationModel> getNotificationModels();

}
