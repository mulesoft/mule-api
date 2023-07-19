/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.connection.serialization;

import org.mule.runtime.api.message.ErrorType;

import java.io.IOException;
import java.io.StringWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

/**
 * Serializer that can convert an {@link ErrorType} into a readable and processable JSON representation and from a JSON
 * {@link String} to an {@link ErrorType} instance
 *
 * @since 1.0
 */
public class ErrorTypeSerializer {

  private static final String IDENTIFIER = "identifier";
  private static final String NAMESPACE = "namespace";
  private static final String PARENT = "parent";

  private ErrorTypeSerializer() {}

  /**
   * Creates a new instance of the {@link ErrorTypeSerializer}. This serializer is capable of serializing and deserializing
   * {@link ErrorType} from JSON ({@link #deserialize(String)} and to JSON ( {@link #serialize(ErrorType)}
   */
  public static ErrorTypeSerializer create() {
    return new ErrorTypeSerializer();
  }

  /**
   * Serializes an {@link ErrorType} into JSON
   *
   * @param errorType {@link ErrorType} to be serialized
   * @return {@link String} JSON representation of the {@link ErrorType}
   */
  public String serialize(ErrorType errorType) throws IOException {
    StringWriter stringWriter = new StringWriter();
    JsonWriter out = new JsonWriter(stringWriter);
    if (errorType == null) {
      out.nullValue();
    } else {
      out.beginObject();
      out.name(IDENTIFIER).value(errorType.getIdentifier());
      out.name(NAMESPACE).value(errorType.getNamespace());
      ErrorType parentErrorType = errorType.getParentErrorType();
      if (parentErrorType != null) {
        out.name(PARENT);
        out.jsonValue(serialize(parentErrorType));
      }
      out.endObject();
    }
    return stringWriter.toString();
  }

  /**
   * Deserializes a JSON representation of an {@link ErrorType}, to an actual instance of it.
   *
   * @param errorType serialized {@link ErrorType}
   * @return an instance of {@link ErrorType} based in the JSON
   */
  public ErrorType deserialize(String errorType) {
    final JsonElement parse = new JsonParser().parse(errorType);
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
