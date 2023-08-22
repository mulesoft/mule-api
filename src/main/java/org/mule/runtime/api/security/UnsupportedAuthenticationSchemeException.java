/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import org.mule.runtime.api.exception.ErrorMessageAwareException;
import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.message.Message;

/**
 * {@code UnsupportedAuthenticationSchemeException} is thrown when a authentication scheme is being used on the message that the
 * Security filter does not understand.
 *
 * @since 1.0
 */
public final class UnsupportedAuthenticationSchemeException extends ServerSecurityException
    implements ErrorMessageAwareException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 8780866132550426151L;

  private final Message errorMessage;

  public UnsupportedAuthenticationSchemeException(I18nMessage message, Message errorMessage) {
    super(message);
    this.errorMessage = errorMessage;
  }

  public UnsupportedAuthenticationSchemeException(I18nMessage message, Throwable cause, Message errorMessage) {
    super(message, cause);
    this.errorMessage = errorMessage;
  }

  @Override
  public Message getErrorMessage() {
    return errorMessage;
  }

}
