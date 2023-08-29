/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.connectivity;

import org.mule.runtime.api.exception.MuleRuntimeException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Exception type that represents a failure when there's no {@link ConnectivityTestingService} that can do connectivity testing
 * over a provided component
 *
 * @since 4.0
 */
public final class UnsupportedConnectivityTestingObjectException extends MuleRuntimeException {

  /**
   * {@inheritDoc}
   */
  public UnsupportedConnectivityTestingObjectException(I18nMessage message) {
    super(message);
  }
}
