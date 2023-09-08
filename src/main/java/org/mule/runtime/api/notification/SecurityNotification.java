/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.security.SecurityException;

/**
 * <code>SecurityNotification</code> is fired when a request for authorisation failed.
 */
public class SecurityNotification extends AbstractServerNotification {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 5951835321289699941L;

  public static final int SECURITY_AUTHENTICATION_FAILED = SECURITY_EVENT_ACTION_START_RANGE + 1;

  static {
    registerAction("authentication failed", SECURITY_AUTHENTICATION_FAILED);
  }

  public SecurityNotification(SecurityException message, int action) {
    super(message.getDetailedMessage(), action);
    resourceIdentifier = message.getClass().getName();
  }

  @Override
  public String getType() {
    return TYPE_WARNING;
  }

  @Override
  public String getEventName() {
    return "SecurityNotification";
  }
}
