/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.persistence;

import org.mule.runtime.api.el.ValidationResult;
import org.mule.runtime.api.el.validation.ValidationMessage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ValidationResultTypeAdapter extends TypeAdapter<ValidationResult> {

  private Gson gson;

  public ValidationResultTypeAdapter(Gson gson) {
    this.gson = gson;
  }

  @Override
  public void write(JsonWriter jsonWriter, ValidationResult validationResult) throws IOException {
    gson.toJson(validationResult, ValidationResult.class, jsonWriter);
  }

  @Override
  public ValidationResult read(JsonReader jsonReader) throws IOException {
    JsonElement jsonElement = new JsonParser().parse(jsonReader);
    if (jsonElement.isJsonNull()) {
      return null;
    }
    JsonObject validationResult = jsonElement.getAsJsonObject();

    boolean isSuccess = validationResult.get("result").getAsBoolean();
    if (isSuccess) {
      return ValidationResult.success();
    }

    String errorMessage = validationResult.get("errorMessage").getAsString();
    List<ValidationMessage> messages = new ArrayList<>();
    validationResult.get("messages").getAsJsonArray()
        .forEach(validationMessage -> messages.add(gson.fromJson(validationMessage, ValidationMessage.class)));

    return ValidationResult.failure(errorMessage, messages);
  }

}
