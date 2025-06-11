/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.cluster;

import org.mule.api.annotation.NoImplement;

/**
 * Provides information about the current status of the cluster.
 * <p>
 * This service can be used to query the state of the cluster, including whether the cluster is active, undergoing maintenance, or
 * in a transitional state. It is useful for components or applications that need to adapt their behavior based on the current
 * cluster status.
 * </p>
 */
@NoImplement
public interface ClusterStatusService {

  /**
   * Retrieves the current {@link ClusterStatus} of the cluster.
   *
   * @return the current cluster status
   */
  ClusterStatus getClusterStatus();
}
