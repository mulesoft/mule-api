/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.exception;

import org.mule.runtime.api.i18n.I18nMessage;
import org.mule.runtime.api.i18n.I18nMessageFactory;

/**
 * <code>MuleException</code> Is the base exception type for the Mule application any other exceptions thrown by Mule code will be
 * based on this exception.
 *
 * @since 1.0
 */
public class DefaultMuleException extends MuleException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 3990670599515417655L;

  public DefaultMuleException(String message) {
    this(I18nMessageFactory.createStaticMessage(message));
  }

  /**
   * @param message the exception message
   */
  public DefaultMuleException(I18nMessage message) {
    super(message);
  }

  public DefaultMuleException(String message, Throwable cause) {
    this(I18nMessageFactory.createStaticMessage(message), cause);
  }

  /**
   * @param message the exception message
   * @param cause   the exception that cause this exception to be thrown
   */
  public DefaultMuleException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public DefaultMuleException(Throwable cause) {
    super(cause);
  }
}
