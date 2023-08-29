/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
