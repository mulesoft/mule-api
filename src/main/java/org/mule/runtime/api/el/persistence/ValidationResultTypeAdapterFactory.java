/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.el.persistence;

import org.mule.runtime.api.el.ValidationResult;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * {@link TypeAdapterFactory} implementation for creating {@link ValidationResult} {@link TypeAdapter}s
 *
 * @since 1.0
 */
public final class ValidationResultTypeAdapterFactory implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
    if (ValidationResult.class.isAssignableFrom(typeToken.getRawType())) {
      return (TypeAdapter<T>) new ValidationResultTypeAdapter(gson);
    }
    return null;
  }
}
