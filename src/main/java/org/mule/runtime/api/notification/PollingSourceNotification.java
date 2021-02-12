/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

public final class PollingSourceNotification extends AbstractServerNotification {

  public static final int ITEM_DISPATCHED = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 1;
  public static final int ITEM_REJECTED_SOURCE_STOPPING = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 2;
  public static final int ITEM_REJECTED_IDEMPOTENCY = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 3;
  public static final int ITEM_REJECTED_WATERMARK = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 4;
  public static final int ITEM_REJECTED_LIMIT = POLLING_SOURCE_EVENT_ACTION_START_RANGE + 5;

  static {
    registerAction("Item dispatched to flow", ITEM_DISPATCHED);
    registerAction("Item rejected because the source is stopping", ITEM_REJECTED_SOURCE_STOPPING);
    registerAction("Item rejected due to idempotency", ITEM_REJECTED_IDEMPOTENCY);
    registerAction("Item rejected due to watermark", ITEM_REJECTED_WATERMARK);
    registerAction("Item rejected because it exceeded the item limit per poll", ITEM_REJECTED_LIMIT);
  }

  public PollingSourceNotification(Object message, int action, String resourceIdentifier) {
    super(message, action, resourceIdentifier);
  }

  @Override
  public String getEventName() {
    return "PollingSourceNotification";
  }
}
