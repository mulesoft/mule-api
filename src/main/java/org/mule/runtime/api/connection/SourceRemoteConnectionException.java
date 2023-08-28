/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
