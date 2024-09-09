/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.artifact;

import static java.util.Optional.empty;

import java.util.Optional;

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

  /**
   * @return the classifier, if set
   * 
   * @since 1.8
   */
  default Optional<String> getClassifier() {
    return empty();
  }

}
