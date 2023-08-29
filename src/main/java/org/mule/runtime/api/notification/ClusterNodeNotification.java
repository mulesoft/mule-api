/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

import org.mule.api.annotation.NoExtend;

/**
 * Notifies when there are a cluster node event
 */
@NoExtend
public class ClusterNodeNotification extends AbstractServerNotification {

  public static final int PRIMARY_CLUSTER_NODE_SELECTED = CLUSTER_NODE_EVENT_ACTION_START_RANGE + 1;

  static {
    registerAction("cluster node selected as primary", PRIMARY_CLUSTER_NODE_SELECTED);
  }

  public ClusterNodeNotification(Object message, int action) {
    super(message, action);
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }

  @Override
  public String getEventName() {
    return "ClusterNodeNotification";
  }
}
