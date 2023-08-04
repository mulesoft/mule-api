/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.security;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Base exception for all security exceptions related to security failures calling external endpoint.
 *
 * @since 4.0
 */
public class ClientSecurityException extends SecurityException {

  public ClientSecurityException(I18nMessage message) {
    super(message);
  }

  public ClientSecurityException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }
}
