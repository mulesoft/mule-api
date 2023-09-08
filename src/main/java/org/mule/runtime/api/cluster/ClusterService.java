/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.cluster;

import org.mule.api.annotation.NoImplement;

/**
 * Clustering service that provides information about the cluster state.
 */
@NoImplement
public interface ClusterService {

  /**
   * @return true if this is the cluster primary instance or if the runtime is not in cluster mode, false otherwise.
   */
  boolean isPrimaryPollingInstance();

}
