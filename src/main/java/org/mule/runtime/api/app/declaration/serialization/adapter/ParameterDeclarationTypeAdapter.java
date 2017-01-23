/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ParameterElementDeclaration}
 *
 * @since 1.0
 */
public class ParameterDeclarationTypeAdapter extends TypeAdapter<ParameterElementDeclaration> {

  private final Gson delegate;

  public ParameterDeclarationTypeAdapter() {
    delegate = new GsonBuilder()
        .registerTypeAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .create();
  }

  @Override
  public void write(JsonWriter out, ParameterElementDeclaration parameter) throws IOException {
    if (parameter != null) {
      out.beginObject();
      out.name(parameter.getName());
      delegate.toJson(parameter.getValue(), new TypeToken<ParameterValue>() {}.getType(), out);
      out.endObject();
    }
  }

  @Override
  public ParameterElementDeclaration read(JsonReader in) throws IOException {
    if (in != null) {
      final JsonElement parse = new JsonParser().parse(in);
      if (parse.isJsonObject() && parse.getAsJsonObject().entrySet().size() == 1) {
        ParameterElementDeclaration declaration = new ParameterElementDeclaration();
        Map.Entry<String, JsonElement> entry = parse.getAsJsonObject().entrySet().iterator().next();
        declaration.setName(entry.getKey());
        declaration.setValue(delegate.fromJson(entry.getValue(), ParameterValue.class));
        return declaration;
      }
    }
    return null;
  }

}
