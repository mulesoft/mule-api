/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.config;

import java.util.Optional;

/**
 * A contract interface for an object that may contain a {@link PoolingProfile}
 *
 * @since 1.0
 */
public interface HasPoolingProfile {

  /**
   * @returns an {@link Optional} {@link PoolingProfile} to configure the connection pool
   */
  Optional<PoolingProfile> getPoolingProfile();
}
