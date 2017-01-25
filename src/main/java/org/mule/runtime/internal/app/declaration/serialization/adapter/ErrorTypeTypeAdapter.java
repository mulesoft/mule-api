/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.mule.runtime.api.message.ErrorType;

import java.io.IOException;

/**
 * {@link TypeAdapter} implementation to be able to serialize {@link ErrorType} instances
 *
 * @since 1.0
 */
public class ErrorTypeTypeAdapter extends TypeAdapter<ErrorType> {

  private static final String IDENTIFIER = "identifier";
  private static final String NAMESPACE = "namespace";
  private static final String PARENT = "parent";

  @Override
  public void write(JsonWriter out, ErrorType value) throws IOException {
    if (value == null) {
      out.nullValue();
    } else {
      out.beginObject();
      out.name(IDENTIFIER).value(value.getIdentifier());
      out.name(NAMESPACE).value(value.getNamespace());
      ErrorType parentErrorType = value.getParentErrorType();
      if (parentErrorType != null) {
        out.name(PARENT);
        write(out, parentErrorType);
      }
      out.endObject();
    }
  }

  @Override
  public ErrorType read(JsonReader in) throws IOException {
    final JsonElement parse = new JsonParser().parse(in);
    return getErrorType(parse);
  }

  private ErrorType getErrorType(JsonElement parse) {
    JsonObject asJsonObject = parse.getAsJsonObject();
    String identifier = asJsonObject.getAsJsonPrimitive(IDENTIFIER).getAsString();
    String namespace = asJsonObject.getAsJsonPrimitive(NAMESPACE).getAsString();
    ErrorType parent = null;
    if (asJsonObject.has(PARENT)) {
      parent = getErrorType(asJsonObject.get(PARENT));
    }
    return new SerializationErrorTypeImplementation(identifier, namespace, parent);
  }

  private class SerializationErrorTypeImplementation implements ErrorType {

    private final String identifier;
    private final String namespace;
    private final ErrorType parent;

    SerializationErrorTypeImplementation(String identifier, String namespace, ErrorType parent) {
      this.identifier = identifier;
      this.namespace = namespace;
      this.parent = parent;
    }

    @Override
    public String getIdentifier() {
      return identifier;
    }

    @Override
    public String getNamespace() {
      return namespace;
    }

    @Override
    public ErrorType getParentErrorType() {
      return parent;
    }
  }
}
