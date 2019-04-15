/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import static java.lang.String.format;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.forExtension;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.COMPONENTS;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONFIG;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONNECTION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONNECTION_FIELD;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.CONSTRUCT;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.DECLARING_EXTENSION;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.GLOBAL_PARAMETER;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.KIND;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.REF_NAME;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.VALUE;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.declareEnrichableElement;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.declareParameterizedElement;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateCustomizableObject;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateIdentifiableObject;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateMetadataAwareObject;
import static org.mule.runtime.internal.app.declaration.serialization.adapter.ElementDeclarationSerializationUtils.populateParameterizedObject;
import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ConfigurationElementDeclaration;
import org.mule.runtime.app.declaration.api.ConstructElementDeclaration;
import org.mule.runtime.app.declaration.api.GlobalElementDeclaration;
import org.mule.runtime.app.declaration.api.GlobalElementDeclarationVisitor;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.ParameterizedElementDeclaration;
import org.mule.runtime.app.declaration.api.TopLevelParameterDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ConfigurationElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ConnectionElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ConstructElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.EnrichableElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterizedElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.TopLevelParameterDeclarer;

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
class GlobalElementDeclarationTypeAdapter extends TypeAdapter<GlobalElementDeclaration> {

  private final Gson delegate;

  GlobalElementDeclarationTypeAdapter(Gson delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(JsonWriter out, GlobalElementDeclaration value) throws IOException {
    final String kind = getKind(value);

    out.beginObject();
    out.name(REF_NAME).value(value.getRefName());
    value.accept(new GlobalElementDeclarationVisitor() {

      @Override
      public void visit(TopLevelParameterDeclaration declaration) {
        populateIdentifiableObject(out, value, kind);
        populateCustomizableObject(delegate, out, value);
        populateMetadataAwareObject(delegate, out, value);
        try {
          out.name(VALUE).jsonValue(delegate.toJson(((TopLevelParameterDeclaration) value).getValue(), ParameterValue.class));
        } catch (IOException e) {
          throw new RuntimeException(format("An error occurred while serializing the declaration of element [%s] of kind [%s] from extension [%s]",
                                            declaration.getName(), kind, declaration.getDeclaringExtension()),
                                     e);
        }
      }

      @Override
      public void visit(ConfigurationElementDeclaration declaration) {
        populateParameterizedObject(delegate, out, (ParameterizedElementDeclaration) value, kind);
        populateConnection(out, (ConfigurationElementDeclaration) value);
      }

      @Override
      public void visit(ConstructElementDeclaration declaration) {
        populateParameterizedObject(delegate, out, (ParameterizedElementDeclaration) value, kind);
        populateComponents(out, (ConstructElementDeclaration) value);
      }
    });

    out.endObject();
  }

  private void populateComponents(JsonWriter out, ConstructElementDeclaration value) {
    try {
      out.name(COMPONENTS).jsonValue(delegate.toJson(value.getComponents()));
    } catch (IOException e) {
      throw new RuntimeException(format("An error occurred while serializing the declaration of components for element [%s] with name [%s] from extension [%s]",
                                        value.getName(), value.getRefName(), value.getDeclaringExtension()),
                                 e);
    }
  }

  @Override
  public GlobalElementDeclaration read(JsonReader in) throws IOException {
    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementName = jsonObject.get(NAME);
      JsonElement elementKind = jsonObject.get(KIND);
      JsonElement elementExtension = jsonObject.get(DECLARING_EXTENSION);
      if (elementKind != null && elementExtension != null && elementName != null) {
        EnrichableElementDeclarer declarer = getDeclarer(forExtension(elementExtension.getAsString()),
                                                         elementKind.getAsString(),
                                                         elementName.getAsString());

        if (elementKind.getAsString().equals(GLOBAL_PARAMETER)) {
          declareGlobalParameter(jsonObject, declarer);
        } else {
          declareParameterizedElement(delegate, jsonObject, (ParameterizedElementDeclarer) declarer);
          if (elementKind.getAsString().equals(CONFIG)) {
            declareConfiguration(jsonObject, (ConfigurationElementDeclarer) declarer);
          } else {
            declareConstruct(jsonObject, (ConstructElementDeclarer) declarer);
          }
        }

        return (GlobalElementDeclaration) declarer.getDeclaration();
      }
    }

    return null;
  }

  private void declareGlobalParameter(JsonObject jsonObject, EnrichableElementDeclarer declarer) {
    declareEnrichableElement(delegate, jsonObject, declarer);
    if (jsonObject.has(REF_NAME)) {
      ((TopLevelParameterDeclarer) declarer).withRefName(jsonObject.get(REF_NAME).getAsString());
    }
    ((TopLevelParameterDeclarer) declarer)
        .withValue((ParameterObjectValue) delegate.fromJson(jsonObject.get(VALUE), ParameterValue.class));
  }

  private void declareConstruct(JsonObject jsonObject, ConstructElementDeclarer declarer) {
    JsonArray components = jsonObject.get(COMPONENTS).getAsJsonArray();
    if (jsonObject.has(REF_NAME)) {
      declarer.withRefName(jsonObject.get(REF_NAME).getAsString());
    }
    components.forEach(c -> declarer
        .withComponent(delegate.fromJson(c, ComponentElementDeclaration.class)));
  }

  private void declareConfiguration(JsonObject configObject, ConfigurationElementDeclarer configDeclarer) {
    configDeclarer.withRefName(configObject.get(REF_NAME).getAsString());

    JsonElement connectionElement = configObject.get(CONNECTION_FIELD);
    if (connectionElement != null && connectionElement.isJsonObject()) {
      JsonObject connectionObject = connectionElement.getAsJsonObject();
      JsonElement elementName = connectionObject.get(NAME);
      JsonElement elementExtension = connectionObject.get(DECLARING_EXTENSION);
      ConnectionElementDeclarer connectionDeclarer = forExtension(elementExtension.getAsString())
          .newConnection(elementName.getAsString());
      declareParameterizedElement(delegate, connectionObject, connectionDeclarer);

      configDeclarer.withConnection(connectionDeclarer.getDeclaration());
    }
  }

  private <T extends EnrichableElementDeclarer> T getDeclarer(ElementDeclarer declarer, String kind, String name) {
    switch (kind) {
      case CONFIG:
        return (T) declarer.newConfiguration(name);
      case GLOBAL_PARAMETER:
        return (T) declarer.newGlobalParameter(name);
      case CONSTRUCT:
        return (T) declarer.newConstruct(name);
      default:
        throw new IllegalArgumentException("Unknown kind: " + kind);
    }
  }

  private String getKind(GlobalElementDeclaration type) {
    if (type instanceof TopLevelParameterDeclaration) {
      return GLOBAL_PARAMETER;
    } else if (type instanceof ConfigurationElementDeclaration) {
      return CONFIG;
    } else if (type instanceof ConstructElementDeclaration) {
      return CONSTRUCT;
    } else {
      throw new IllegalArgumentException("Unknown kind for type: " + type.getClass().getName());
    }
  }

  private void populateConnection(JsonWriter out, ConfigurationElementDeclaration config) {

    try {
      if (config.getConnection().isPresent()) {
        out.name(CONNECTION_FIELD).beginObject();
        populateParameterizedObject(delegate, out, config.getConnection().get(), CONNECTION);
        out.endObject();
      }
    } catch (IOException e) {
      throw new RuntimeException(
                                 format("An error occurred while serializing the connection provider declaration of element [%s] of kind [%s] from extension [%s]",
                                        config.getName(), "config", config.getDeclaringExtension()),
                                 e);
    }
  }

}
