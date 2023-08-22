/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
