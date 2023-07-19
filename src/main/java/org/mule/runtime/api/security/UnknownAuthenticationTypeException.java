/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
