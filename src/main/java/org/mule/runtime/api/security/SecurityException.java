/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.security;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@code SecurityException} is a generic security exception
 *
 * @since 1.0
 */
public class SecurityException extends MuleException {

  public SecurityException(I18nMessage message) {
    super(message);
  }

  public SecurityException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

}
