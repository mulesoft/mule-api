/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.notification;

import java.util.Set;

/**
 * Indicates that the current model can declare which {@link NotificationModel} it fires.
 *
 * Do not create custom implementations of this interface. The Mule Runtime should be the only one providing implementations
 * of it.
 *
 * @since 1.1
 */
public interface HasNotifications {

  /**
   * @return a {@link Set} of {@link NotificationModel} with the notifications that the current component fires.
   *
   * @see NotificationModel
   */
  Set<NotificationModel> getNotificationModels();

}
