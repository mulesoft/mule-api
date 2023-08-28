/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.tx;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.api.i18n.I18nMessage;

/**
 * TransactionException is thrown when an exception occurs while trying to create, start commit or rollback
 *
 * @since 1.0
 */
public class TransactionException extends MuleException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -1077047092280362809L;

  /**
   * @param message the exception message
   */
  public TransactionException(I18nMessage message) {
    super(message);
  }

  /**
   * @param message the exception message
   * @param cause   the exception that cause this exception to be thrown
   */
  public TransactionException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public TransactionException(Throwable cause) {
    super(cause);
  }
}
