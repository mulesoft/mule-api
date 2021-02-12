/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import java.time.LocalDateTime;

public class PollingSourceNotificationInfo {

  private LocalDateTime dispatchTimestamp;
  private String pollId;

  public PollingSourceNotificationInfo(String pollId) {
    this.pollId = pollId;
    this.dispatchTimestamp = LocalDateTime.now();
  }
}
