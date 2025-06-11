package org.mule.runtime.api.cluster;

/**
 * Represents the state of a cluster, which controls the availability and behavior of cluster-wide operations such as
 * repartitioning. Changing the cluster state allows you to control how and when certain operations (e.g., joining nodes,
 * performing maintenance) are triggered.
 *
 * Typical use cases for changing the cluster state include:
 * <ul>
 * <li>Adding multiple new members to the cluster without triggering repartitioning after each join</li>
 * <li>Performing cluster maintenance without impacting data distribution</li>
 * </ul>
 */
public enum ClusterState {

  /**
   * Normal operational mode of the cluster. All cluster operations, including repartitioning, are allowed.
   */
  ACTIVE,

  /**
   * Allows multiple new members to be added to the cluster without triggering repartitioning after each join. This is useful when
   * scaling the cluster in bulk to avoid intermediate rebalancing steps.
   */
  NO_MIGRATION,

  /**
   * Disables repartitioning to allow safe maintenance on the cluster. No data movement occurs, ensuring stability during
   * maintenance windows.
   */
  FROZEN,

  /**
   * Internal state used during a graceful, cluster-wide shutdown. Only read operations are permitted.
   */
  PASSIVE,

  /**
   * Internal transitional state indicating that the cluster is in the process of switching states. Not intended for long-term use
   * or external management.
   */
  IN_TRANSITION,
}
