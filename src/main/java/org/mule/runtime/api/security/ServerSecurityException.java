/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.security;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * Base exception for all security exceptions related to security enforced within the mule runtime.
 * 
 * @since 4.0
 */
public class ServerSecurityException extends SecurityException {

  public ServerSecurityException(I18nMessage message) {
    super(message);
  }

  public ServerSecurityException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }
}
