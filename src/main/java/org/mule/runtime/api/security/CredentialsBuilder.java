/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.security;

import org.mule.api.annotation.NoImplement;

/**
 * Builder for a {@link Credentials} implementation.
 *
 * @since 1.0
 */
@NoImplement
public interface CredentialsBuilder {

  /**
   * @param username the username of this {@link Credentials}
   * @return {@code this} builder
   */
  CredentialsBuilder withUsername(String username);

  /**
   * @param password the password of this {@link Credentials}
   * @return {@code this} builder
   */
  CredentialsBuilder withPassword(char[] password);

  /**
   * @param roles the enabled roles of this {@link Credentials}
   * @return {@code this} builder
   */
  CredentialsBuilder withRoles(Object roles);

  /**
   * @return an instance of a default implementation of {@link Credentials}
   */
  Credentials build();
}
