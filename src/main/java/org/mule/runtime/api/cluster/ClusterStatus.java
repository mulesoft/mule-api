/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.cluster;

/**
 * Represents the current status of a cluster, including its size, state, and safety.
 * <p>
 * This interface provides insight into the operational health and configuration of the cluster, allowing components or external
 * tools to make informed decisions, such as initiating maintenance or scaling operations.
 * </p>
 */
public interface ClusterStatus {

  /**
   * Returns the number of nodes currently participating in the cluster.
   *
   * @return the total number of active cluster members
   */
  int getClusterSize();

  /**
   * Returns the current operational {@link ClusterState} of the cluster.
   * <p>
   * This indicates whether the cluster is active, frozen, in transition, or in another defined state.
   * </p>
   *
   * @return the current cluster state
   */
  ClusterState getClusterState();

  /**
   * Indicates whether the cluster is in a "safe" state.
   * <p>
   * A cluster is considered safe if:
   * <ul>
   * <li>There are no active partition migrations</li>
   * <li>Each partition has a sufficient number of backups</li>
   * </ul>
   * When this method returns {@code true}, it is safe to shut down a node without risking data loss.
   * </p>
   *
   * @return {@code true} if the cluster is in a safe state; {@code false} otherwise
   */
  boolean isClusterSafe();
}
