/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
