/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterizedElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ParameterSimpleValue;
import org.mule.runtime.api.app.declaration.fluent.ParameterizedElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

/**
 * An abstract {@link TypeAdapter} for serializing instances of {@link ParameterizedElementDeclaration}
 *
 * @since 1.0
 */
abstract class ParameterizedElementDeclarationTypeAdapter<T> extends TypeAdapter<T> {

  private static final String KIND = "kind";
  public static final String PROPERTIES = "properties";
  protected final Gson delegate;

  ParameterizedElementDeclarationTypeAdapter(Gson delegate) {
    this.delegate = delegate;
  }

  protected JsonWriter populateParameterizedObject(JsonWriter out, ParameterizedElementDeclaration declaration, String kind)
      throws IOException {
    out.name("name").value(declaration.getName());
    out.name("declaringExtension").value(declaration.getDeclaringExtension());
    out.name("kind").value(kind);
    out.name("parameters").jsonValue(delegate.toJson(declaration.getParameters()));
    out.name("customParameters").jsonValue(delegate.toJson(declaration.getCustomParameters()));
    out.name("properties").jsonValue(delegate.toJson(declaration.getProperties()));
    return out;
  }

  protected <T extends ParameterizedElementDeclarer> T declareParameterizedElement(JsonObject jsonObject,
                                                                                   ParameterizedElementDeclarer declarer) {
    JsonArray parameters = jsonObject.get("parameters").getAsJsonArray();
    JsonArray customParameters = jsonObject.get("customParameters").getAsJsonArray();

    parameters.forEach(p -> {
      ParameterElementDeclaration param = delegate.fromJson(p, ParameterElementDeclaration.class);
      declarer.withParameter(param.getName(), param.getValue());
    });

    customParameters.forEach(p -> {
      ParameterElementDeclaration param = delegate.fromJson(p, ParameterElementDeclaration.class);
      declarer.withCustomParameter(param.getName(), ((ParameterSimpleValue) param.getValue()).getValue());
    });

    Map<String, Object> properties = delegate.fromJson(jsonObject.get(PROPERTIES), new TypeToken<Map<String, String>>() {

    }.getType());
    properties.forEach(declarer::withProperty);

    return (T) declarer;
  }

}
