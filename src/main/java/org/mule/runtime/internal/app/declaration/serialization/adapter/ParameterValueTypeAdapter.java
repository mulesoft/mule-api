/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.cdata;
import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.plain;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.FIELDS;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.TYPE_ID;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.ParameterValueVisitor;
import org.mule.runtime.app.declaration.api.fluent.ParameterListValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue;
import org.mule.runtime.app.declaration.api.fluent.SimpleValueType;

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

  private static final String TEXT = "text";
  private static final String IS_CDATA = "isCData";
  private static final String TYPE = "type";

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
      public void visitSimpleValue(ParameterSimpleValue text) {
        writeSimpleValue(text, jsonWriter);
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

      writeObjectParameters(objectValue, object);

      if (objectValue.getTypeId() != null && !objectValue.getTypeId().trim().isEmpty()) {
        object.name(TYPE_ID).value(objectValue.getTypeId());
      }
      object.endObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void writeObjectParameters(ParameterObjectValue objectValue, JsonWriter object) throws IOException {
    object.name(FIELDS).beginObject();
    objectValue.getParameters().forEach((name, v) -> {
      try {
        JsonWriter field = object.name(name);
        v.accept(getValueVisitor(field));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    object.endObject();
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

  private void writeSimpleValue(ParameterSimpleValue value, JsonWriter jsonWriter) {
    try {
      jsonWriter.beginObject();
      jsonWriter.name(TEXT).value(value.getValue());
      jsonWriter.name(IS_CDATA).value(value.isCData());

      if (value.getType() != null) {
        jsonWriter.name(TYPE).value(value.getType().toString());
      }
      jsonWriter.endObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private ParameterValue getParameterValue(JsonElement jsonElement) {
    if (jsonElement.isJsonArray()) {
      return loadListValue(jsonElement);
    }

    if (jsonElement.isJsonObject()) {
      JsonObject object = jsonElement.getAsJsonObject();
      JsonElement text = object.get(TEXT);
      if (text != null && object.get(IS_CDATA) != null) {
        JsonElement type = object.get(TYPE);
        SimpleValueType typeValue = type == null ? SimpleValueType.STRING : SimpleValueType.valueOf(type.getAsString());
        return object.get(IS_CDATA).getAsBoolean() ? cdata(text.getAsString(), typeValue) : plain(text.getAsString(), typeValue);
      } else {
        return loadObjectValue(object);
      }
    }
    return null;
  }

  private ParameterValue loadListValue(JsonElement jsonElement) {
    JsonArray array = jsonElement.getAsJsonArray();
    ParameterListValue.Builder listBuilder = ParameterListValue.builder();
    array.forEach(e -> listBuilder.withValue(getParameterValue(e)));
    return listBuilder.build();
  }

  private ParameterValue loadObjectValue(JsonObject object) {
    ParameterObjectValue.Builder objectBuilder = ParameterObjectValue.builder();
    JsonElement id = object.get(TYPE_ID);
    if (id != null) {
      objectBuilder.ofType(id.getAsString());
    }

    JsonElement parameters = object.get(FIELDS);
    if (parameters != null) {
      parameters.getAsJsonObject().entrySet()
          .forEach(field -> objectBuilder.withParameter(field.getKey(), getParameterValue(field.getValue())));
    }
    return objectBuilder.build();
  }
}
