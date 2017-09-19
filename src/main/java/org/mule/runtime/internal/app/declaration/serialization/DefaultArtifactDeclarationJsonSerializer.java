/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization;

import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationTypeAdapterFactory;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ParameterGroupElementDeclarationTypeAdapter;
import org.mule.runtime.internal.app.declaration.serialization.adapter.ParameterValueTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Default implementation of an {@link ArtifactDeclarationJsonSerializer}
 *
 * @since 1.0
 */
public class DefaultArtifactDeclarationJsonSerializer implements ArtifactDeclarationJsonSerializer {

  private boolean prettyPrint;

  public DefaultArtifactDeclarationJsonSerializer(boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
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

  private Gson createGson() {
    GsonBuilder gsonBuilder = new GsonBuilder()
        .registerTypeAdapterFactory(new ElementDeclarationTypeAdapterFactory())
        .registerTypeAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .registerTypeAdapter(ParameterGroupElementDeclaration.class, new ParameterGroupElementDeclarationTypeAdapter());

    if (prettyPrint) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder.create();
  }

}

