/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
