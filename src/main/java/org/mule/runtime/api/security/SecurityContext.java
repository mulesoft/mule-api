/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
