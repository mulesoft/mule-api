/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.GlobalElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * {@link TypeAdapterFactory} implementation for creating {@link ElementDeclaration} {@link TypeAdapter}s
 *
 * @since 1.0
 */
public class ElementDeclarationTypeAdapterFactory implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (GlobalElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new GlobalElementDeclarationTypeAdapter(gson);
    } else if (RouteElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new RouteElementDeclarationTypeAdapter(gson);
    } else if (ComponentElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new ComponentElementDeclarationTypeAdapter(gson);
    }

    return null;
  }

}
