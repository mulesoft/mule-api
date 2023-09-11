/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;
import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Signals that a remote error in the connection occurred.
 *
 * @since 4.3.0, 4.2.3
 */
public class RemoteConnectionException extends MuleRuntimeException {

  public RemoteConnectionException(String message) {
    super(createStaticMessage(message), null);
  }

  public RemoteConnectionException(String message, Throwable cause) {
    super(createStaticMessage(message), cause);
  }

}
