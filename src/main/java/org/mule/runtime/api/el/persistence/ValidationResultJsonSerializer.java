/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mule.runtime.api.el.ValidationResult;

public class ValidationResultJsonSerializer {

  private Gson gson;

  public ValidationResultJsonSerializer() {
    gson = new GsonBuilder().registerTypeAdapter(ValidationResult.class, new ValidationResultTypeAdapter()).create();
  }

  public String serialize(ValidationResult validationResult) {
    return this.gson.toJson(validationResult);
  }

  public ValidationResult deserialize(String validationResult) {
    return this.gson.fromJson(validationResult, ValidationResult.class);
  }

}
