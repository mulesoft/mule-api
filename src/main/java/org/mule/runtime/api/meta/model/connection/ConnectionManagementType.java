/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.connection;

/**
 * Enumerates the different strategies that the runtime supports for managing connections.
 *
 * @since 1.0
 */
public enum ConnectionManagementType {
  /**
   * Connections are pooled
   */
  POOLING,

  /**
   * Connections are cached
   */
  CACHED,

  /**
   * Connections are created upon operation execution and destroyed when operation finished
   */
  NONE
}
