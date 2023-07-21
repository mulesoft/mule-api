/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.security;

import org.mule.api.annotation.NoImplement;

/**
 * {@code Credentials} holds credentials information for a user.
 *
 * @since 1.0
 */
@NoImplement
public interface Credentials {

  /**
   * @return the {@code username} for this {@link Credentials}
   */
  String getUsername();

  /**
   * @return the {@code password} for this {@link Credentials}
   */
  char[] getPassword();

  /**
   * @return the supported {@code roles} for this {@link Credentials}
   */
  Object getRoles();

}
