/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization;

import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.serialization.api.ElementDeclarationJsonSerializer;

/**
 * Default implementation of an {@link ArtifactDeclarationJsonSerializer}
 *
 * @since 1.0
 */
public class DefaultArtifactDeclarationJsonSerializer implements ArtifactDeclarationJsonSerializer {

  private ElementDeclarationJsonSerializer delegateSerializer;

  public DefaultArtifactDeclarationJsonSerializer(boolean prettyPrint) {
    this.delegateSerializer = ElementDeclarationJsonSerializer.getDefault(prettyPrint);
  }

  /**
   * Serializes an {@link ArtifactDeclaration} into JSON
   *
   * @param declaration {@link ArtifactDeclaration} to be serialized
   * @return {@link String} JSON representation of the {@link ArtifactDeclaration}
   */
  public String serialize(ArtifactDeclaration declaration) {
    return delegateSerializer.serialize(declaration);
  }

  /**
   * Deserializes a JSON representation of an {@link ArtifactDeclaration}, to an actual instance of it.
   *
   * @param declaration serialized {@link ArtifactDeclaration}
   * @return an instance of {@link ArtifactDeclaration} based in the JSON
   */
  public ArtifactDeclaration deserialize(String declaration) {
    return delegateSerializer.deserialize(declaration, ArtifactDeclaration.class);
  }
}

