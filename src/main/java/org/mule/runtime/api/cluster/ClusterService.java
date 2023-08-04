/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
