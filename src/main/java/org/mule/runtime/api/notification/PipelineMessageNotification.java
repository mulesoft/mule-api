/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import static org.slf4j.LoggerFactory.getLogger;

import org.mule.runtime.api.component.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.slf4j.Logger;

/**
 * <code>PipelineMessageNotification</code> is fired at key steps in the processing of {@link Pipeline}
 */
public final class PipelineMessageNotification extends EnrichedServerNotification {

  private static final long serialVersionUID = 6065691696506216248L;

  private static final Logger LOGGER = getLogger(PipelineMessageNotification.class);

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

  /**
   * Get the failing component if the pipeline has failed.
   *
   * @return An optional with the failing component.
   */
  public Optional<Component> getFailingComponent() {
    if (getException() == null) {
      return empty();
    }
    try {
      return of((Component) getException().getClass().getMethod("getFailingComponent").invoke(getException()));
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassCastException e) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(format("Error accessing failing component for: %s", getException()), e);
      }
      return empty();
    }
  }
}
