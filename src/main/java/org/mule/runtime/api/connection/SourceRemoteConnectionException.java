/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.connection;

/**
 * Signals that a remote error in the connection occurred related to the source
 *
 * @since 4.3.0, 4.2.3
 */
public class SourceRemoteConnectionException extends RemoteConnectionException {

  public SourceRemoteConnectionException(String message) {
    super(message);
  }

  public SourceRemoteConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

}
