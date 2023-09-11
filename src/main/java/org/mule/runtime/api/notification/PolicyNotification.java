/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import org.mule.runtime.api.component.location.ComponentLocation;

/**
 * Used to notify that a message was processed by a policy
 */
public final class PolicyNotification extends EnrichedServerNotification {

  // Fired when processing of policy chain starts
  public static final int PROCESS_START = POLICY_MESSAGE_EVENT_ACTION_START_RANGE + 1;
  // Fired just before processing the next chain
  public static final int BEFORE_NEXT = POLICY_MESSAGE_EVENT_ACTION_START_RANGE + 2;
  // Fired just after returning from the process of the next chain
  public static final int AFTER_NEXT = POLICY_MESSAGE_EVENT_ACTION_START_RANGE + 3;
  // Fired when policy processing returns after processing request and response message
  public static final int PROCESS_END = POLICY_MESSAGE_EVENT_ACTION_START_RANGE + 4;

  static {
    registerAction("Policy processing start", PROCESS_START);
    registerAction("Policy next chain processing start", BEFORE_NEXT);
    registerAction("Policy next chain processing end", AFTER_NEXT);
    registerAction("Policy processing end", PROCESS_END);
  }

  private String policyId;

  public PolicyNotification(String policyId, EnrichedNotificationInfo notificationInfo, int action,
                            ComponentLocation componentLocation) {
    super(notificationInfo, action, componentLocation);
    this.policyId = policyId;
  }

  public String getPolicyId() {
    return policyId;
  }

  @Override
  public String getEventName() {
    return "PolicyNotification";
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }
}
