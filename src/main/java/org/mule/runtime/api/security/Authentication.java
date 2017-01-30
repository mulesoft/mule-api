/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import java.io.Serializable;
import java.util.Map;

/**
 * {@code Authentication} represents an authentication request and contains authentication information
 * if the request was successful.
 *
 * @since 1.0
 */
public interface Authentication extends Serializable {

  /**
   * @param authenticated {@code true} if {@code this} {@link Authentication} has been authenticated
   */
  void setAuthenticated(boolean authenticated);

  /**
   * @return {@code true} if {@code this} {@link Authentication} has been authenticated
   */
  boolean isAuthenticated();

  /**
   * @return a representation of the credentials for {@code this} {@link Authentication}
   */
  Object getCredentials();

  /**
   * @return the principal part of {@code this} {@link Authentication} (ie, the User for BasicAuth, the Subject for SAML, etc)
   */
  Object getPrincipal();

  /**
   * @return the properties of {@code this} {@link Authentication}
   */
  Map<String, Object> getProperties();

  /**
   * @param properties a group of properties available for {@code this} {@link Authentication}
   */
  void setProperties(Map<String, Object> properties);
}
