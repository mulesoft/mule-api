/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.exception.MuleException;

/**
 * {@code UnknownAuthenticationTypeException} is thrown if a security context request is make with an unrecognised Authentication
 * type.
 *
 * @since 1.0
 */
public final class UnknownAuthenticationTypeException extends MuleException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -994458254811668986L;

  public UnknownAuthenticationTypeException(Authentication authentication) {
    super(createStaticMessage("The authentication type %s is not recognised by the Security Manager",
                              (authentication == null ? "null" : authentication.getClass().getName())));
  }
}
