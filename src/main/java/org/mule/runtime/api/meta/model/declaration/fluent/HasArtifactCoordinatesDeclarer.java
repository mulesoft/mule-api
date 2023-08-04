/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.artifact.ArtifactCoordinates;

/**
 * Contract interface for a declarer in which it's possible to add {@link ArtifactCoordinates}
 *
 * @since 1.5
 */
@NoImplement
public interface HasArtifactCoordinatesDeclarer {

  /**
   * Adds the given {@code stereotype}
   *
   * @param artifactCoordinates a {@link ArtifactCoordinates}
   * @return {@code this} declarer
   */
  ExtensionDeclarer withArtifactCoordinates(ArtifactCoordinates artifactCoordinates);

}
