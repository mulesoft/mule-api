/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

public final class PollingSourceNotification extends AbstractServerNotification {

  public static final int POLL_STARTED = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 1;
  public static final int POLL_SUCCESS = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 2;
  public static final int POLL_FAILURE = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 3;
  public static final int POLL_SOURCE_STOPPING = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 4;

  private String pollId;

  static {
    registerAction("Poll started", POLL_STARTED);
    registerAction("Poll successfully completed", POLL_SUCCESS);
    registerAction("Poll failed to complete", POLL_FAILURE);
    registerAction("Polling Source Stopping", POLL_SOURCE_STOPPING);
  }

  public PollingSourceNotification(int action, String resourceIdentifier, String pollId) {
    super("", action, resourceIdentifier);
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
