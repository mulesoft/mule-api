/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.COMPONENTS;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONFIG_REF;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.DECLARING_EXTENSION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.FLOW;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.KIND;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.OPERATION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.ROUTER;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.ROUTES;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.SCOPE;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.SOURCE;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.declareParameterizedElement;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateParameterizedObject;
import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.FlowElementDeclaration;
import org.mule.runtime.api.app.declaration.OperationElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;
import org.mule.runtime.api.app.declaration.RouterElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;
import org.mule.runtime.api.app.declaration.SourceElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ComponentElementDeclarer;
import org.mule.runtime.api.app.declaration.fluent.ElementDeclarer;
import org.mule.runtime.api.app.declaration.fluent.RouterElementDeclarer;
import org.mule.runtime.api.app.declaration.fluent.ScopeElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
    if (value instanceof RouterElementDeclaration) {
      out.name(ROUTES).beginArray();
      ((RouterElementDeclaration) value).getRoutes().forEach(r -> delegate.toJson(r, RouteElementDeclaration.class, out));
      out.endArray();
    } else if (value instanceof ScopeElementDeclaration) {
      out.name(COMPONENTS).jsonValue(delegate.toJson(((ScopeElementDeclaration) value).getComponents()));
    }

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
        ComponentElementDeclarer declarer = getDeclarer(ElementDeclarer.forExtension(elementExtension.getAsString()),
                                                        elementKind.getAsString(),
                                                        elementName.getAsString());

        declareParameterizedElement(delegate, jsonObject, declarer);
        JsonElement configRef = jsonObject.get(CONFIG_REF);
        if (configRef != null) {
          declarer.withConfig(configRef.getAsString());
        }

        if (elementKind.getAsString().equals(ROUTER)) {
          declareRouter(jsonObject, (RouterElementDeclarer) declarer);
        } else if (elementKind.getAsString().equals(SCOPE)) {
          declareScope(jsonObject, declarer);
        }
        return (ComponentElementDeclaration) declarer.getDeclaration();
      }
    }
    return null;
  }

  private void declareRouter(JsonObject jsonObject, RouterElementDeclarer declarer) {
    JsonElement routes = jsonObject.get(ROUTES);
    if (routes != null && routes.isJsonArray()) {
      routes.getAsJsonArray().forEach(route -> declarer
          .withRoute(delegate.fromJson(route, RouteElementDeclaration.class)));
    }
  }

  private void declareScope(JsonObject jsonObject, ComponentElementDeclarer declarer) {
    if (jsonObject.get(COMPONENTS) != null) {
      JsonArray components = jsonObject.get(COMPONENTS).getAsJsonArray();
      components.forEach(c -> ((ScopeElementDeclarer) declarer)
          .withComponent(delegate.fromJson(c, ComponentElementDeclaration.class)));
    }
  }

  private <T extends ComponentElementDeclarer> T getDeclarer(ElementDeclarer declarer, String kind, String name) {
    switch (kind) {
      case OPERATION:
        return (T) declarer.newOperation(name);
      case SOURCE:
        return (T) declarer.newSource(name);
      case SCOPE:
        return (T) declarer.newScope(name);
      case ROUTER:
        return (T) declarer.newRouter(name);
      default:
        throw new IllegalArgumentException("Unknown kind: " + kind);
    }
  }

  private String getKind(ComponentElementDeclaration type) {
    if (type instanceof OperationElementDeclaration) {
      return OPERATION;
    } else if (type instanceof SourceElementDeclaration) {
      return SOURCE;
    } else if (type instanceof RouterElementDeclaration) {
      return ROUTER;
    } else if (type instanceof FlowElementDeclaration) {
      return FLOW;
    } else if (type instanceof ScopeElementDeclaration) {
      return SCOPE;
    } else {
      throw new IllegalArgumentException("Unknown kind for type: " + type.getName());
    }
  }
}
