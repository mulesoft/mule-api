/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.app.declaration.serialization.adapter;

import static java.lang.String.format;

import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.CustomizableElementDeclaration;
import org.mule.runtime.app.declaration.api.IdentifiableElementDeclaration;
import org.mule.runtime.app.declaration.api.MetadataPropertiesAwareElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterizedElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.EnrichableElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.HasNestedComponentDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterizedElementDeclarer;
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

  static final String SCOPE = "SCOPE";
  static final String ROUTE = "ROUTE";
  static final String CHAIN = "CHAIN";
  static final String SOURCE = "SOURCE";
  static final String ROUTER = "ROUTER";
  static final String CONFIG = "CONFIG";
  static final String COMPONENT = "COMPONENT";
  static final String CONSTRUCT = "CONSTRUCT";
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

  static void populateIdentifiableObject(JsonWriter out, IdentifiableElementDeclaration declaration, String kind) {
    try {
      out.name(NAME).value(declaration.getName());
      out.name(DECLARING_EXTENSION).value(declaration.getDeclaringExtension());
      out.name(KIND).value(kind);
    } catch (IOException e) {
      throw new RuntimeException(
                                 format("An error occurred while serializing the declaration of element [%s] of kind [%s] from extension [%s]",
                                        declaration.getName(), kind, declaration.getDeclaringExtension()),
                                 e);
    }
  }

  static void populateCustomizableObject(Gson delegate, JsonWriter out, CustomizableElementDeclaration declaration) {
    try {
      out.name(CUSTOM_PARAMETERS).jsonValue(delegate.toJson(declaration.getCustomConfigurationParameters()));
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while serializing the declaration of a customizable element: "
          + e.getMessage(), e);
    }
  }

  static void populateMetadataAwareObject(Gson delegate, JsonWriter out,
                                          MetadataPropertiesAwareElementDeclaration declaration) {
    try {
      out.name(PROPERTIES).jsonValue(delegate.toJson(declaration.getMetadataProperties()));
    } catch (IOException e) {
      throw new RuntimeException("An error occurred while serializing the declaration of a metadata aware element: "
          + e.getMessage(), e);
    }
  }

  static JsonWriter populateParameterizedObject(Gson delegate, JsonWriter out,
                                                ParameterizedElementDeclaration declaration, String kind) {

    populateIdentifiableObject(out, declaration, kind);
    populateCustomizableObject(delegate, out, declaration);
    populateMetadataAwareObject(delegate, out, declaration);

    try {
      out.name(PARAMETER_GROUPS).beginArray();
      for (ParameterGroupElementDeclaration group : declaration.getParameterGroups()) {
        out.jsonValue(delegate.toJson(group));
      }
      out.endArray();
      return out;

    } catch (IOException e) {
      throw new RuntimeException("An error occurred while serializing the declaration of a parameterized element: "
          + e.getMessage(), e);
    }
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

  static <T extends ParameterizedElementDeclarer> T declareComposableElement(Gson delegate,
                                                                             JsonObject jsonObject,
                                                                             HasNestedComponentDeclarer declarer) {
    JsonArray components = jsonObject.get(COMPONENTS).getAsJsonArray();
    components.forEach(component -> declarer.withComponent(delegate.fromJson(component, ComponentElementDeclaration.class)));

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
