/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.deployment.persistence;

import org.mule.runtime.api.deployment.meta.MulePluginModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializer capable of marshalling an {@link MulePluginModel} instance
 * to {@code JSON} format and back
 * TODO MULE-11007: this class should be moved to a persistence module
 *
 * @since 1.0
 */
public class MulePluginModelJsonSerializer {

  private final Gson gson;

  public MulePluginModelJsonSerializer() {
    gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
  }

  /**
   * Serializes the given {@code muleArtifactDescriber} to JSON
   *
   * @param mulePluginModel the describer to be marshaled
   * @return the resulting {@code JSON} in {@link String} format
   */
  public String serialize(MulePluginModel mulePluginModel) {
    return gson.toJson(mulePluginModel);
  }

  /**
   * Deserializes the given {@code JSON} back into a {@link MulePluginModel}
   *
   * @param json a describer {@code JSON} in {@link String} format
   * @return an {@link MulePluginModel} instance
   */
  public MulePluginModel deserialize(String json) {
    return gson.fromJson(json, MulePluginModel.class);
  }
}
