/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;


import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.artifact.ArtifactCoordinates;

import java.util.Optional;

/**
 * Contract interface for a {@link BaseDeclaration} in which it's possible to add/get {@link ArtifactCoordinates} objects
 *
 * @since 1.5
 */
@NoImplement
public interface WithArtifactCoordinatesDeclaration {

  /**
   * @return {@code this} component's {@link ArtifactCoordinates}
   */
  Optional<ArtifactCoordinates> getArtifactCoordinates();

  /**
   * @param artifactCoordinates {@code this} component's {@link ArtifactCoordinates}
   */
  void withArtifactCoordinates(ArtifactCoordinates artifactCoordinates);

}
