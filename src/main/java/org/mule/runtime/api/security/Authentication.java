/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import java.io.Serializable;
import java.util.Map;

/**
 * {@code Authentication} represents an authentication request and contains authentication information if the request was
 * successful.
 * <p>
 * Implementations must be immutable.
 *
 * @since 1.0
 */
public interface Authentication extends Serializable {

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
   * Makes a copy of this {@link Authentication} with the passed properties set.
   * 
   * @param properties a group of properties available for the returned {@link Authentication}. MAy be {@code null}
   */
  Authentication setProperties(Map<String, Object> properties);
}
