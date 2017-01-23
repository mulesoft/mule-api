/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.api.app.declaration.ParameterValueVisitor;
import org.mule.runtime.api.app.declaration.fluent.ParameterListValue;
import org.mule.runtime.api.app.declaration.fluent.ParameterObjectValue;
import org.mule.runtime.api.app.declaration.fluent.ParameterSimpleValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ParameterValue}
 *
 * @since 1.0
 */
public class ParameterValueTypeAdapter extends TypeAdapter<ParameterValue> {

  private static final String TYPE_ID = "typeId";

  @Override
  public void write(JsonWriter jsonWriter, ParameterValue parameter) throws IOException {
    if (parameter != null) {
      parameter.accept(getValueVisitor(jsonWriter));
    }
  }

  @Override
  public ParameterValue read(JsonReader in) throws IOException {
    if (in != null) {
      final JsonElement parse = new JsonParser().parse(in);
      return getParameterValue(parse);
    }
    return null;
  }

  private ParameterValueVisitor getValueVisitor(final JsonWriter jsonWriter) {
    return new ParameterValueVisitor() {

      @Override
      public void visitSimpleValue(String value) {
        writeSimpleValue(value, jsonWriter);
      }

      @Override
      public void visitListValue(ParameterListValue list) {
        writeListValue(list, jsonWriter);
      }

      @Override
      public void visitObjectValue(ParameterObjectValue objectValue) {
        writeObjectValue(objectValue, jsonWriter);
      }
    };
  }

  private void writeObjectValue(ParameterObjectValue objectValue, JsonWriter jsonWriter) {
    try {
      JsonWriter object = jsonWriter.beginObject();
      objectValue.getParameters().forEach((name, v) -> {
        try {
          JsonWriter field = object.name(name);
          v.accept(getValueVisitor(field));
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });
      if (objectValue.getTypeId() != null && !objectValue.getTypeId().trim().isEmpty()) {
        object.name(TYPE_ID).value(objectValue.getTypeId());
      }
      object.endObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void writeListValue(ParameterListValue list, JsonWriter jsonWriter) {
    List<ParameterValue> values = list.getValues();
    try {
      JsonWriter array = jsonWriter.beginArray();
      values.forEach(v -> v.accept(getValueVisitor(array)));
      array.endArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void writeSimpleValue(String value, JsonWriter jsonWriter) {
    try {
      jsonWriter.value(value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private ParameterValue getParameterValue(JsonElement jsonElement) {
    if (jsonElement.isJsonPrimitive()) {
      String value = jsonElement.getAsString();
      return ParameterSimpleValue.of(value);
    }

    if (jsonElement.isJsonArray()) {
      JsonArray array = jsonElement.getAsJsonArray();
      ParameterListValue.Builder listBuilder = ParameterListValue.builder();
      array.forEach(e -> listBuilder.withValue(getParameterValue(e)));
      return listBuilder.build();
    }

    if (jsonElement.isJsonObject()) {
      JsonObject object = jsonElement.getAsJsonObject();
      ParameterObjectValue.Builder objectBuilder = ParameterObjectValue.builder();
      object.entrySet().forEach(field -> {
        if (field.getKey().equals(TYPE_ID)) {
          objectBuilder.ofType(field.getValue().getAsString());
        } else {
          objectBuilder.withParameter(field.getKey(), getParameterValue(field.getValue()));
        }
      });
      return objectBuilder.build();
    }
    return null;
  }
}
