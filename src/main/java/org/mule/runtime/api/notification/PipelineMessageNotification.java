/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import org.mule.runtime.api.component.Component;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * <code>PipelineMessageNotification</code> is fired at key steps in the processing of {@link Pipeline}
 */
public final class PipelineMessageNotification extends EnrichedServerNotification {

  private static final long serialVersionUID = 6065691696506216248L;

  // Fired when processing of pipeline starts
  public static final int PROCESS_START = PIPELINE_MESSAGE_EVENT_ACTION_START_RANGE + 1;
  // Fired when pipeline processing reaches the end before returning
  public static final int PROCESS_END = PIPELINE_MESSAGE_EVENT_ACTION_START_RANGE + 2;
  // Fired when pipeline processing returns after processing request and response message
  public static final int PROCESS_COMPLETE = PIPELINE_MESSAGE_EVENT_ACTION_START_RANGE + 4;

  static {
    registerAction("pipeline process start", PROCESS_START);
    registerAction("pipeline request message processing end", PROCESS_END);
    registerAction("pipeline process complete", PROCESS_COMPLETE);
  }

  public PipelineMessageNotification(EnrichedNotificationInfo notificationInfo, String name, int action) {
    super(notificationInfo, action,
          notificationInfo.getComponent().getRootContainerLocation() != null
              ? notificationInfo.getComponent().getRootContainerLocation().getGlobalName()
              : null);
    this.resourceIdentifier = name;
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }

  @Override
  public String getEventName() {
    return "PipelineMessageNotification";
  }

  public Optional<Component> getFailingComponent() {
    if (getException() == null) {
      return empty();
    }
    try {
      Field failingComponentField = getException().getClass().getDeclaredField("failingComponent");
      failingComponentField.setAccessible(true);
      return of((Component) failingComponentField.get(getException()));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return empty();
    }
  }
}
