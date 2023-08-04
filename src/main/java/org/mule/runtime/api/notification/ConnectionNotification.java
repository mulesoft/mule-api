/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.notification;

/**
 * Is fired by a connector when a connection is made or disconnected. A disconnection can be caused by network failure, JMX, or
 * the server shutting down.
 */
public final class ConnectionNotification extends AbstractServerNotification {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -6455441938378523145L;
  public static final int CONNECTION_CONNECTED = CONNECTION_EVENT_ACTION_START_RANGE + 1;
  public static final int CONNECTION_FAILED = CONNECTION_EVENT_ACTION_START_RANGE + 2;
  public static final int CONNECTION_DISCONNECTED = CONNECTION_EVENT_ACTION_START_RANGE + 3;

  static {
    registerAction("connected", CONNECTION_CONNECTED);
    registerAction("connect failed", CONNECTION_FAILED);
    registerAction("disconnected", CONNECTION_DISCONNECTED);
  }

  public ConnectionNotification(String description, String identifier, int action) {
    super((description == null ? identifier : description), action);
    resourceIdentifier = identifier;
  }

  @Override
  public String getType() {
    if (action == CONNECTION_DISCONNECTED) {
      return TYPE_WARNING;
    }
    if (action == CONNECTION_FAILED) {
      return TYPE_ERROR;
    }
    return TYPE_INFO;
  }

  @Override
  public String getEventName() {
    return "ConnectionNotification";
  }
}
