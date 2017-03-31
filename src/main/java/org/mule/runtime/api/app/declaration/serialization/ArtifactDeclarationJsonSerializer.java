/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.serialization;

import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.internal.app.declaration.serialization.DefaultArtifactDeclarationJsonSerializer;

/**
 * Serializer that can convert an {@link ArtifactDeclaration} into a readable and processable JSON representation and from a JSON
 * {@link String} to an {@link ArtifactDeclaration} instance
 *
 * @since 1.0
 */
public interface ArtifactDeclarationJsonSerializer {

  /**
   * Creates a new instance of the {@link ArtifactDeclarationJsonSerializer}.
   * This serializer is capable of serializing and deserializing {@link ArtifactDeclaration}
   * from JSON ({@link #deserialize(String)} and to JSON ( {@link #serialize(ArtifactDeclaration)}
   */
  static ArtifactDeclarationJsonSerializer getDefault(boolean prettyPrint) {
    return new DefaultArtifactDeclarationJsonSerializer(prettyPrint);
  }

  /**
   * Serializes an {@link ArtifactDeclaration} into JSON
   *
   * @param declaration {@link ArtifactDeclaration} to be serialized
   * @return {@link String} JSON representation of the {@link ArtifactDeclaration}
   */
  String serialize(ArtifactDeclaration declaration);

  /**
   * Deserializes a JSON representation of an {@link ArtifactDeclaration}, to an actual instance of it.
   *
   * @param declaration serialized {@link ArtifactDeclaration}
   * @return an instance of {@link ArtifactDeclaration} based in the JSON
   */
  ArtifactDeclaration deserialize(String declaration);

}

