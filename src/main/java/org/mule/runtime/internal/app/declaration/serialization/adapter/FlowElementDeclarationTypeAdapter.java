/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.FlowElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ElementDeclarer;
import org.mule.runtime.api.app.declaration.fluent.FlowElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * A {@link TypeAdapter} for serializing instances of {@link FlowElementDeclaration}
 *
 * @since 1.0
 */
class FlowElementDeclarationTypeAdapter extends ParameterizedElementDeclarationTypeAdapter<FlowElementDeclaration> {

  public FlowElementDeclarationTypeAdapter(Gson gson) {
    super(gson);
  }

  @Override
  public void write(JsonWriter out, FlowElementDeclaration flow) throws IOException {
    if (flow != null) {
      out.beginObject();
      populateParameterizedObject(out, flow, "FLOW");
      out.name("components").jsonValue(delegate.toJson(flow.getComponents()));
      out.endObject();
    }
  }

  @Override
  public FlowElementDeclaration read(JsonReader in) throws IOException {
    if (in != null) {
      final JsonElement parse = new JsonParser().parse(in);
      if (parse.isJsonObject()) {
        JsonObject jsonObject = parse.getAsJsonObject();
        JsonElement elementKind = jsonObject.get("kind");
        if (elementKind != null && elementKind.getAsString().equals("FLOW")) {
          String name = jsonObject.get("name").getAsString();
          JsonArray components = jsonObject.get("components").getAsJsonArray();

          FlowElementDeclarer declarer = ElementDeclarer.newFlow(name);
          declareParameterizedElement(jsonObject, declarer);
          components.forEach(c -> declarer.withComponent(delegate.fromJson(c, ComponentElementDeclaration.class)));

          return declarer.getDeclaration();
        }
      }
    }
    return null;
  }

}
