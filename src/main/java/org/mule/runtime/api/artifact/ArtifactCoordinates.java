/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.artifact;

/**
 * The coordinates that identify an artifact, in the form of a Maven GAV
 *
 * @since 1.5
 */
public interface ArtifactCoordinates {

  /**
   * @return The groupId
   */
  String getGroupId();

  /**
   * @return The artifactId
   */
  String getArtifactId();

  /**
   * @return The version
   */
  String getVersion();
}
