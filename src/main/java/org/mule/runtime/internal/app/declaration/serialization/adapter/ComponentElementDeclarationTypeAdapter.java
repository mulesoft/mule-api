/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
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
class ComponentElementDeclarationTypeAdapter extends ParameterizedElementDeclarationTypeAdapter<ComponentElementDeclaration> {

  private static final String OPERATION = "OPERATION";
  private static final String SOURCE = "SOURCE";
  private static final String SCOPE = "SCOPE";
  private static final String ROUTER = "ROUTER";
  private static final String KIND = "kind";

  ComponentElementDeclarationTypeAdapter(Gson delegate) {
    super(delegate);
  }

  @Override
  public void write(JsonWriter out, ComponentElementDeclaration value) throws IOException {
    out.beginObject();
    populateParameterizedObject(out, value, getKind(value));
    if (value.getConfigRef() != null && !value.getConfigRef().trim().isEmpty()) {
      out.name("configRef").value(value.getConfigRef());
    }
    if (value instanceof RouterElementDeclaration) {
      out.name("routes").beginArray();
      ((RouterElementDeclaration) value).getRoutes().forEach(r -> delegate.toJson(r, RouteElementDeclaration.class, out));
      out.endArray();
    } else if (value instanceof ScopeElementDeclaration) {
      out.name("route");
      delegate.toJson(((ScopeElementDeclaration) value).getRoute(), RouteElementDeclaration.class, out);
    }

    out.endObject();
  }

  @Override
  public ComponentElementDeclaration read(JsonReader in) throws IOException {
    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementKind = jsonObject.get(KIND);
      JsonElement elementExtension = jsonObject.get("declaringExtension");
      JsonElement elementName = jsonObject.get("name");
      if (elementKind != null && elementExtension != null && elementName != null) {
        ComponentElementDeclarer declarer = getDeclarer(ElementDeclarer.forExtension(elementExtension.getAsString()),
                                                        elementKind.getAsString(),
                                                        elementName.getAsString());

        declareParameterizedElement(jsonObject, declarer);
        JsonElement configRef = jsonObject.get("configRef");
        if (configRef != null) {
          declarer.withConfig(configRef.getAsString());
        }

        if (elementKind.getAsString().equals(ROUTER)) {
          JsonElement routes = jsonObject.get("routes");
          if (routes != null && routes.isJsonArray()) {
            routes.getAsJsonArray().forEach(route -> ((RouterElementDeclarer) declarer)
                .withRoute(delegate.fromJson(route, RouteElementDeclaration.class)));
          }
        } else if (elementKind.getAsString().equals(SCOPE)) {
          declareParameterizedElement(jsonObject, declarer);
          JsonElement route = jsonObject.get("route");
          if (route != null) {
            ((ScopeElementDeclarer) declarer).withRoute(delegate.fromJson(route, RouteElementDeclaration.class));
          }
        }
        return (ComponentElementDeclaration) declarer.getDeclaration();
      }
    }
    return null;
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
    } else if (type instanceof ScopeElementDeclaration) {
      return SCOPE;
    } else {
      throw new IllegalArgumentException("Unknown kind for type: " + type.getName());
    }
  }
}
