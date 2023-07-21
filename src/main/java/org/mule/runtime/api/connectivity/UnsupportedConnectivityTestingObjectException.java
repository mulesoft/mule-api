/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
