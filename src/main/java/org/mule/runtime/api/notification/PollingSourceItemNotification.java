/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import static org.mule.runtime.api.notification.PollingSourceNotification.POLL_SOURCE_STOPPING;

import org.mule.api.annotation.Experimental;

import java.io.Serializable;
import java.util.Optional;

@Experimental
public class PollingSourceItemNotification extends AbstractServerNotification {

  public static final int ITEM_DISPATCHED = POLL_SOURCE_STOPPING + 1;
  public static final int ITEM_REJECTED_SOURCE_STOPPING = POLL_SOURCE_STOPPING + 2;
  public static final int ITEM_REJECTED_IDEMPOTENCY = POLL_SOURCE_STOPPING + 3;
  public static final int ITEM_REJECTED_WATERMARK = POLL_SOURCE_STOPPING + 4;
  public static final int ITEM_REJECTED_LIMIT = POLL_SOURCE_STOPPING + 5;

  static {
    registerAction("Item dispatched to flow", ITEM_DISPATCHED);
    registerAction("Item rejected because the source is stopping", ITEM_REJECTED_SOURCE_STOPPING);
    registerAction("Item rejected due to idempotency", ITEM_REJECTED_IDEMPOTENCY);
    registerAction("Item rejected due to watermark", ITEM_REJECTED_WATERMARK);
    registerAction("Item rejected because it exceeded the item limit per poll", ITEM_REJECTED_LIMIT);
  }

  private String pollId;
  private String itemId;
  private Optional<Serializable> watermark;
  private String eventId;

  public PollingSourceItemNotification(String pollId, String itemId, Optional<Serializable> watermark, int action,
                                       String eventId, String componentLocation) {
    super("", action, componentLocation);
    this.pollId = pollId;
    this.itemId = itemId;
    this.watermark = watermark;
    this.eventId = eventId;
  }

  public String getPollId() {
    return pollId;
  }

  public String getItemId() {
    return itemId;
  }

  public Optional<Serializable> getWatermark() {
    return watermark;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  @Override
  public String getEventName() {
    return "PollingSourceItemNotification";
  }
}
