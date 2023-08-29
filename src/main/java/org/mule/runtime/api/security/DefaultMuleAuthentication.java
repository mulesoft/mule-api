/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;

/**
 * Default implementation of {@link Authentication}
 *
 * @since 1.0
 */
public final class DefaultMuleAuthentication implements Authentication {

  private static final long serialVersionUID = -8958045048390093902L;

  private final char[] credentials;
  private final String user;
  private final Map<String, Object> properties;

  public DefaultMuleAuthentication(Credentials credentials) {
    this.user = credentials.getUsername();
    this.credentials = credentials.getPassword();
    this.properties = null;
  }

  private DefaultMuleAuthentication(String user, char[] credentials, Map<String, Object> properties) {
    this.user = user;
    this.credentials = credentials;
    this.properties = properties != null ? unmodifiableMap(properties) : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getCredentials() {
    return new String(credentials);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getPrincipal() {
    return user;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getProperties() {
    return properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultMuleAuthentication setProperties(Map<String, Object> properties) {
    return new DefaultMuleAuthentication(user, credentials, properties);
  }
}
