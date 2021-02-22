/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

public class PollingSourceNotificationInfo {

  private String pollId;

  public PollingSourceNotificationInfo(String pollId) {
    this.pollId = pollId;
    // The notification already has the timestamp
    // TODO: see what information is needed, it might be possible that this class is not necessary.
  }
}
