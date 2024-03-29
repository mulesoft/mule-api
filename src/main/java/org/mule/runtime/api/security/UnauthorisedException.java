/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

import org.mule.runtime.api.i18n.I18nMessage;

/**
 * {@code UnauthorisedException} is thrown if authentication fails.
 *
 * @since 1.0
 */
public class UnauthorisedException extends ServerSecurityException {

  /**
   * Serial version
   */
  private static final long serialVersionUID = -8830402477997253918L;

  public UnauthorisedException(I18nMessage message) {
    super(message);
  }

  public UnauthorisedException(I18nMessage message, Throwable cause) {
    super(message, cause);
  }

  public UnauthorisedException(SecurityContext context, String filter, String connector) {
    super(constructMessage(context, connector, filter));
  }

  private static I18nMessage constructMessage(SecurityContext context, String originatingConnectorName, String filter) {
    I18nMessage m;
    if (context == null) {
      m = createStaticMessage("Registered authentication is set to %s but there was no security context on the session",
                              filter);
    } else {
      m = createStaticMessage("Authentication failed for principal %s", context.getAuthentication().getPrincipal());
    }
    m.setNextMessage(createStaticMessage("Authentication denied on connector %s", originatingConnectorName));
    return m;
  }

}
