/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.el.persistence;

import org.mule.runtime.api.el.ValidationResult;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class ValidationResultJsonSerializer {

  private Gson gson;

  public ValidationResultJsonSerializer() {
    gson = new GsonBuilder().registerTypeAdapterFactory(new ValidationResultTypeAdapterFactory()).create();
  }

  public String serialize(ValidationResult validationResult) {
    return this.gson.toJson(validationResult);
  }

  public ValidationResult deserialize(String validationResult) {
    return this.gson.fromJson(validationResult, ValidationResult.class);
  }

}
