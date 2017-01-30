/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.security;

/**
 * {@code Credentials} holds credentials information for a user.
 *
 * @since 1.0
 */
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
