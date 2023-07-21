/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
