/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * <code>NotPermittedException</code> is thrown if the user isn't authorized to perform an action.
 *
 * @since 1.0
 */
public final class NotPermittedException extends ServerSecurityException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = 287768744221027152L;

  public NotPermittedException(I18nMessage message) {
    super(message);
  }

}
