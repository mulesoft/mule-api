/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import org.mule.runtime.api.app.declaration.CustomizableElementDeclaration;
import org.mule.runtime.api.app.declaration.IdentifiableElementDeclaration;
import org.mule.runtime.api.app.declaration.MetadataPropertiesAwareElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterGroupElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterizedElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.EnrichableElementDeclarer;
import org.mule.runtime.api.app.declaration.fluent.ParameterSimpleValue;
import org.mule.runtime.api.app.declaration.fluent.ParameterizedElementDeclarer;
import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Util class used across all the type adapters related to the {@link ArtifactDeclarationJsonSerializer}.
 * This is an internal utils class, not to be considered part of the API. Backwards compatibility not guaranteed.
 *
 * @since 1.0
 */
final class ElementDeclarationSerializationUtils {

  static final String FLOW = "FLOW";
  static final String SCOPE = "SCOPE";
  static final String ROUTE = "ROUTE";
  static final String SOURCE = "SOURCE";
  static final String ROUTER = "ROUTER";
  static final String CONFIG = "CONFIG";
  static final String OPERATION = "OPERATION";
  static final String CONNECTION = "CONNECTION";
  static final String GLOBAL_PARAMETER = "GLOBAL_PARAMETER";

  static final String NAME = "name";
  static final String KIND = "kind";
  static final String VALUE = "value";
  static final String FIELDS = "fields";
  static final String ROUTES = "routes";
  static final String TYPE_ID = "typeId";
  static final String REF_NAME = "refName";
  static final String CONFIG_REF = "configRef";
  static final String PARAMETER_GROUPS = "parameterGroups";
  static final String PARAMETERS = "parameters";
  static final String COMPONENTS = "components";
  static final String CONNECTION_FIELD = "connection";
  static final String PROPERTIES = "metadataProperties";
  static final String DECLARING_EXTENSION = "declaringExtension";
  static final String CUSTOM_PARAMETERS = "customConfigurationParameters";

  static void populateIdentifiableObject(JsonWriter out, IdentifiableElementDeclaration declaration, String kind)
      throws IOException {
    out.name(NAME).value(declaration.getName());
    out.name(DECLARING_EXTENSION).value(declaration.getDeclaringExtension());
    out.name(KIND).value(kind);
  }

  static void populateCustomizableObject(Gson delegate, JsonWriter out, CustomizableElementDeclaration declaration)
      throws IOException {
    out.name(CUSTOM_PARAMETERS).jsonValue(delegate.toJson(declaration.getCustomConfigurationParameters()));
  }

  static void populateMetadataAwareObject(Gson delegate, JsonWriter out,
                                          MetadataPropertiesAwareElementDeclaration declaration)
      throws IOException {
    out.name(PROPERTIES).jsonValue(delegate.toJson(declaration.getMetadataProperties()));
  }

  static JsonWriter populateParameterizedObject(Gson delegate, JsonWriter out,
                                                ParameterizedElementDeclaration declaration, String kind)
      throws IOException {

    populateIdentifiableObject(out, declaration, kind);
    populateCustomizableObject(delegate, out, declaration);
    populateMetadataAwareObject(delegate, out, declaration);

    out.name(PARAMETER_GROUPS).beginArray();
    for (ParameterGroupElementDeclaration group : declaration.getParameterGroups()) {
      out.jsonValue(delegate.toJson(group));
    }
    out.endArray();
    return out;
  }

  static <T extends ParameterizedElementDeclarer> T declareParameterizedElement(Gson delegate,
                                                                                JsonObject jsonObject,
                                                                                ParameterizedElementDeclarer declarer) {
    declareEnrichableElement(delegate, jsonObject, declarer);

    JsonArray groups = jsonObject.get(PARAMETER_GROUPS).getAsJsonArray();
    groups.forEach(group -> ((ParameterizedElementDeclaration) declarer.getDeclaration())
        .addParameterGroup(delegate.fromJson(group, ParameterGroupElementDeclaration.class)));

    return (T) declarer;
  }

  static <T extends EnrichableElementDeclarer> T declareEnrichableElement(Gson delegate, JsonObject jsonObject,
                                                                          EnrichableElementDeclarer declarer) {
    JsonArray customParameters = jsonObject.get(CUSTOM_PARAMETERS).getAsJsonArray();
    customParameters.forEach(p -> {
      ParameterElementDeclaration param = delegate.fromJson(p, ParameterElementDeclaration.class);
      declarer.withCustomParameter(param.getName(), ((ParameterSimpleValue) param.getValue()).getValue());
    });

    Map<String, Serializable> properties = delegate.fromJson(jsonObject.get(PROPERTIES), new TypeToken<Map<String, String>>() {

    }.getType());
    properties.forEach(declarer::withProperty);

    return (T) declarer;
  }

}
