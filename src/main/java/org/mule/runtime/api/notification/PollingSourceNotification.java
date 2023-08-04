/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

import org.mule.api.annotation.Experimental;

/**
 * Notification fired by polling sources that communicate events related to the polling action (start, success, failure). It also
 * carries information about the resourceId that triggered the poll as well as an identifier for that particular poll event.
 *
 * @since 1.5
 */
@Experimental
public final class PollingSourceNotification extends AbstractServerNotification {

  public static final int POLL_STARTED = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 1;
  public static final int POLL_SUCCESS = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 2;
  public static final int POLL_FAILURE = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 3;

  private final String pollId;

  static {
    registerAction("Poll started", POLL_STARTED);
    registerAction("Poll successfully completed", POLL_SUCCESS);
    registerAction("Poll failed to complete", POLL_FAILURE);
  }

  public PollingSourceNotification(int action, String componentLocation, String pollId) {
    super("", action, componentLocation);
    this.pollId = pollId;
  }

  public String getPollId() {
    return pollId;
  }

  @Override
  public String getEventName() {
    return "PollingSourceNotification";
  }
}
