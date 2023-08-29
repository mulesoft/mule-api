/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.api.notification.PollingSourceNotification.POLL_FAILURE;

import org.mule.api.annotation.Experimental;

import java.io.Serializable;
import java.util.Optional;

/**
 * Notifications of this type will be sent for each item processed during a poll indicating status, pollId, itemId, watermark,
 * resourceIdentifier and Event Id. If the item is rejected, the Event Id will be an empty string and, if the item is dispatched
 * to the runtime, the Event Id will be populated so that flow executions can be tied to a poll item, and it's originating poll.
 *
 * @since 1.5
 */
@Experimental
public class PollingSourceItemNotification extends AbstractServerNotification {

  public static final int ITEM_DISPATCHED = POLL_FAILURE + 1;
  public static final int ITEM_REJECTED_SOURCE_STOPPING = POLL_FAILURE + 2;
  public static final int ITEM_REJECTED_IDEMPOTENCY = POLL_FAILURE + 3;
  public static final int ITEM_REJECTED_WATERMARK = POLL_FAILURE + 4;

  static {
    registerAction("Item dispatched to flow", ITEM_DISPATCHED);
    registerAction("Item rejected because the source is stopping", ITEM_REJECTED_SOURCE_STOPPING);
    registerAction("Item rejected due to idempotency", ITEM_REJECTED_IDEMPOTENCY);
    registerAction("Item rejected due to watermark", ITEM_REJECTED_WATERMARK);
  }

  private final String pollId;
  private final String itemId;
  private final Serializable watermark;
  private final String eventId;

  public PollingSourceItemNotification(int action, String pollId, String itemId, Serializable watermark,
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
    return ofNullable(watermark);
  }

  public Optional<String> getEventId() {
    return ofNullable(eventId);
  }

  @Override
  public String getEventName() {
    return "PollingSourceItemNotification";
  }
}
