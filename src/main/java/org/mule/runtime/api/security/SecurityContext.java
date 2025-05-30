/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import java.io.Serializable;

/**
 * {@code SecurityContext} holds security information and is associated with the MuleSession.
 *
 * @since 1.0
 */
public interface SecurityContext extends Serializable {

  /**
   * @return the current {@link Authentication} in the context
   */
  Authentication getAuthentication();

  /**
   * @param authentication the {@link Authentication} to set in the current context
   */
  void setAuthentication(Authentication authentication);

}
