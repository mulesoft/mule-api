/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import java.io.Serializable;
import java.util.Optional;

public class PollingSourceNotificationInfo {

  private static String NO_ID = "";

  private String pollId;
  private String itemId;
  private Serializable watermark;
  private String status;

  public PollingSourceNotificationInfo(String pollId, Optional<String> itemId, Optional<Serializable> watermark, String status) {
    this.pollId = pollId;
    this.itemId = itemId.orElse(NO_ID);
    this.watermark = watermark.orElse(null);
    this.status = status;
  }

  public String getPollId() {
    return pollId;
  }

  public String getItemId() {
    return itemId;
  }

  public Serializable getWatermark() {
    return watermark;
  }

  public String getStatus() {
    return status;
  }
}
