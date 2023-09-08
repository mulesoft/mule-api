/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.notification;

/**
 * <code>FlowConstructNotification</code> is fired when an event such as the flow construct starting occurs. The payload of this
 * event will always be a reference to the flow construct.
 */
public final class FlowConstructNotification extends AbstractServerNotification {

  private static final long serialVersionUID = 6658641434183647952L;
  public static final int FLOW_CONSTRUCT_INITIALISED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 1;
  public static final int FLOW_CONSTRUCT_STARTED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 2;
  public static final int FLOW_CONSTRUCT_STOPPED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 3;
  public static final int FLOW_CONSTRUCT_PAUSED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 4;
  public static final int FLOW_CONSTRUCT_RESUMED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 5;
  public static final int FLOW_CONSTRUCT_DISPOSED = FLOW_CONSTRUCT_EVENT_ACTION_START_RANGE + 6;

  static {
    registerAction("flow construct initialised", FLOW_CONSTRUCT_INITIALISED);
    registerAction("flow construct started", FLOW_CONSTRUCT_STARTED);
    registerAction("flow construct stopped", FLOW_CONSTRUCT_STOPPED);
    registerAction("flow construct paused", FLOW_CONSTRUCT_PAUSED);
    registerAction("flow construct resumed", FLOW_CONSTRUCT_RESUMED);
    registerAction("flow construct disposed", FLOW_CONSTRUCT_DISPOSED);
  }

  public FlowConstructNotification(String name, int action) {
    super(name, action);
    resourceIdentifier = name;
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }

  @Override
  public String getEventName() {
    return "FlowConstructNotification";
  }
}
