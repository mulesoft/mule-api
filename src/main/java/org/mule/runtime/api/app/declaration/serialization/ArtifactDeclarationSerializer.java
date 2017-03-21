/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.serialization;

import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationTypeAdapterFactory;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ParameterDeclarationTypeAdapter;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ParameterValueTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializer that can convert an {@link ArtifactDeclaration} into a readable and processable JSON representation and from a JSON
 * {@link String} to an {@link ArtifactDeclaration} instance
 *
 * @since 1.0
 */
public class ArtifactDeclarationSerializer {

  private boolean prettyPrint;

  private ArtifactDeclarationSerializer() {}

  /**
   * Creates a new instance of the {@link ArtifactDeclarationSerializer}.
   * This serializer is capable of serializing and deserializing {@link ArtifactDeclaration}
   * from JSON ({@link #deserialize(String)} and to JSON ( {@link #serialize(ArtifactDeclaration)}
   */
  public static ArtifactDeclarationSerializer create() {
    return new ArtifactDeclarationSerializer();
  }

  /**
   * Serializes an {@link ArtifactDeclaration} into JSON
   *
   * @param declaration {@link ArtifactDeclaration} to be serialized
   * @return {@link String} JSON representation of the {@link ArtifactDeclaration}
   */
  public String serialize(ArtifactDeclaration declaration) {
    return createGson().toJson(declaration);
  }

  /**
   * Deserializes a JSON representation of an {@link ArtifactDeclaration}, to an actual instance of it.
   *
   * @param declaration serialized {@link ArtifactDeclaration}
   * @return an instance of {@link ArtifactDeclaration} based in the JSON
   */
  public ArtifactDeclaration deserialize(String declaration) {
    return createGson().fromJson(declaration, ArtifactDeclaration.class);
  }

  /**
   * Configures the output Json that fits in a page for pretty printing. This option only affects Json serialization format.
   *
   * @return this {@code ArtifactDeclarationSerializer}
   */
  public ArtifactDeclarationSerializer setPrettyPrint() {
    this.prettyPrint = true;
    return this;
  }

  private Gson createGson() {
    GsonBuilder gsonBuilder = new GsonBuilder()
        .registerTypeAdapterFactory(new ElementDeclarationTypeAdapterFactory())
        .registerTypeAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .registerTypeAdapter(ParameterElementDeclaration.class, new ParameterDeclarationTypeAdapter());

    if (prettyPrint) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder.create();
  }

}

