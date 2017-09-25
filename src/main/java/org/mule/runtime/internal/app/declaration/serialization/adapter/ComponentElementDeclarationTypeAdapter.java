/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.COMPONENTS;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONFIG_REF;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONSTRUCT;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.DECLARING_EXTENSION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.KIND;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.OPERATION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.ROUTE;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.SOURCE;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.declareComposableElement;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.declareParameterizedElement;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateParameterizedObject;

import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ConstructElementDeclaration;
import org.mule.runtime.app.declaration.api.OperationElementDeclaration;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;
import org.mule.runtime.app.declaration.api.SourceElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ComponentElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.HasNestedComponentDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ParameterizedElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ComponentElementDeclaration}
 *
 * @since 1.0
 */
class ComponentElementDeclarationTypeAdapter extends TypeAdapter<ComponentElementDeclaration> {

  private final Gson delegate;

  ComponentElementDeclarationTypeAdapter(Gson delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(JsonWriter out, ComponentElementDeclaration value) throws IOException {
    out.beginObject();
    populateParameterizedObject(delegate, out, value, getKind(value));
    if (value.getConfigRef() != null && !value.getConfigRef().trim().isEmpty()) {
      out.name(CONFIG_REF).value(value.getConfigRef());
    }
    out.name(COMPONENTS).jsonValue(delegate.toJson(value.getComponents()));
    out.endObject();
  }

  @Override
  public ComponentElementDeclaration read(JsonReader in) throws IOException {
    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementKind = jsonObject.get(KIND);
      JsonElement elementExtension = jsonObject.get(DECLARING_EXTENSION);
      JsonElement elementName = jsonObject.get(NAME);
      if (elementKind != null && elementExtension != null && elementName != null) {
        ParameterizedElementDeclarer declarer = getDeclarer(ElementDeclarer.forExtension(elementExtension.getAsString()),
                                                            elementKind.getAsString(),
                                                            elementName.getAsString());

        declareParameterizedElement(delegate, jsonObject, declarer);

        if (declarer instanceof HasNestedComponentDeclarer) {
          declareComposableElement(delegate, jsonObject, (HasNestedComponentDeclarer) declarer);
        }

        JsonElement configRef = jsonObject.get(CONFIG_REF);
        if (configRef != null && declarer instanceof ComponentElementDeclarer) {
          ((ComponentElementDeclarer) declarer).withConfig(configRef.getAsString());
        }

        return (ComponentElementDeclaration) declarer.getDeclaration();
      }
    }
    return null;
  }

  private <T extends ParameterizedElementDeclarer> T getDeclarer(ElementDeclarer declarer, String kind, String name) {
    switch (kind) {
      case OPERATION:
        return (T) declarer.newOperation(name);
      case SOURCE:
        return (T) declarer.newSource(name);
      case CONSTRUCT:
        return (T) declarer.newConstruct(name);
      case ROUTE:
        return (T) declarer.newRoute(name);
      default:
        throw new IllegalArgumentException("Unknown kind: " + kind);
    }
  }

  private String getKind(ComponentElementDeclaration type) {
    if (type instanceof OperationElementDeclaration) {
      return OPERATION;
    } else if (type instanceof SourceElementDeclaration) {
      return SOURCE;
    } else if (type instanceof ConstructElementDeclaration) {
      return CONSTRUCT;
    } else if (type instanceof RouteElementDeclaration) {
      return ROUTE;
    } else {
      throw new IllegalArgumentException("Unknown kind for type: " + type.getName());
    }
  }
}
