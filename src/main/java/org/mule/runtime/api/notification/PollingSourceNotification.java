/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import java.time.LocalDateTime;
import java.util.List;

public final class PollingSourceNotification extends AbstractServerNotification {

  public static final int POLL_SUCCESS = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 1;
  public static final int POLL_FAILURE = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 2;

  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String pollId;
  private List<PollingSourceNotificationInfo> acceptedItems;
  private List<PollingSourceNotificationInfo> rejectedItems;

  static {
    registerAction("Poll successfully completed", POLL_SUCCESS);
    registerAction("Poll failed to complete", POLL_FAILURE);
  }

  public PollingSourceNotification(int action, String resourceIdentifier, List<PollingSourceNotificationInfo> itemNotifications, LocalDateTime startTime, String pollId) {
    super(itemNotifications, action, resourceIdentifier);
    this.startTime = startTime;
    this.endTime = LocalDateTime.now();
    this.pollId = pollId;
    processItems(itemNotifications);
  }

  @Override
  public String getEventName() {
    return "PollingSourceNotification";
  }

  public List<PollingSourceNotificationInfo> getAcceptedItems() {
    return acceptedItems;
  }

  public List<PollingSourceNotificationInfo> getRejectedItems() {
    return rejectedItems;
  }

  public void processItems(List<PollingSourceNotificationInfo> itemNotifications) {
    for (PollingSourceNotificationInfo item : itemNotifications) {
      if (item.getStatus().equals("ACCEPTED")) {
        acceptedItems.add(item);
      } else {
        rejectedItems.add(item);
      }
    }
  }
}
