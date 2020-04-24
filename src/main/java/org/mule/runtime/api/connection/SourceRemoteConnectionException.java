/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connection;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Signals that an remote error in the connection occurred related to the source
 *
 * @since 4.4.0
 */
public class SourceRemoteConnectionException extends RemoteConnectionException {

  public SourceRemoteConnectionException(I18nMessage message) {
    super(message);
  }

  public SourceRemoteConnectionException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

}
