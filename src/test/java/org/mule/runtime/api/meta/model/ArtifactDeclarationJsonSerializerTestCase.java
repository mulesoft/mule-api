/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newListValue;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newObjectValue;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newParameterGroup;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.CONNECTION;
import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ElementDeclarer;
import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class ArtifactDeclarationJsonSerializerTestCase {

  public static final String EXPECTED_ARTIFACT_DECLARATION_JSON = "declaration/artifact-declaration.json";
  private ArtifactDeclaration applicationDeclaration;

  @Before
  public void setup() {
    applicationDeclaration = createArtifact();
  }

  @Test
  public void serializationTest() throws IOException {
    String expected = IOUtils.toString(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(EXPECTED_ARTIFACT_DECLARATION_JSON));

    String json = ArtifactDeclarationJsonSerializer.getDefault(true).serialize(applicationDeclaration);
    assertThat(json, json.trim(), is(equalTo(expected.trim())));
  }

  @Test
  public void serializeDeserializeTest() throws IOException {
    ArtifactDeclarationJsonSerializer serializer = ArtifactDeclarationJsonSerializer.getDefault(true);
    String json = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration artifactDeclaration = serializer.deserialize(json);
    assertThat(json, applicationDeclaration, is(equalTo(artifactDeclaration)));
  }

  private ArtifactDeclaration createArtifact() {
    ElementDeclarer db = ElementDeclarer.forExtension("Database");
    ElementDeclarer http = ElementDeclarer.forExtension("HTTP");
    ElementDeclarer sockets = ElementDeclarer.forExtension("Sockets");
    ElementDeclarer core = ElementDeclarer.forExtension("Mule Core");
    ElementDeclarer wsc = ElementDeclarer.forExtension("Web Service Consumer");

    return newArtifact()
        .withGlobalElement(db.newConfiguration("config")
            .withRefName("dbConfig")
            .withConnection(db
                .newConnection("derby-connection")
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("poolingProfile", newObjectValue()
                        .withParameter("maxPoolSize", "10")
                        .build())
                    .withParameter("connectionProperties", newObjectValue()
                        .withParameter("first", "propertyOne")
                        .withParameter("second", "propertyTwo")
                        .build())
                    .withParameter("database", "target/muleEmbeddedDB")
                    .withParameter("create", "true")
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(
                           http.newConfiguration("listenerConfig")
                               .withRefName("httpListener")
                               .withParameterGroup(newParameterGroup()
                                   .withParameter("basePath", "/")
                                   .getDeclaration())
                               .withConnection(http.newConnection("listener-connection")
                                   .withParameterGroup(newParameterGroup(CONNECTION)
                                       .withParameter("tlsContext", newObjectValue()
                                           .withParameter("key-store", newObjectValue()
                                               .withParameter("path", "ssltest-keystore.jks")
                                               .withParameter("password", "changeit")
                                               .withParameter("keyPassword", "changeit")
                                               .build())
                                           .build())
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("host", "localhost")
                                       .withParameter("port", "49019")
                                       .withParameter("protocol", "HTTPS")
                                       .getDeclaration())
                                   .getDeclaration())
                               .getDeclaration())
        .withGlobalElement(
                           http.newConfiguration("request-config")
                               .withRefName("httpRequester")
                               .withConnection(http.newConnection("request-connection")
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("host", "localhost")
                                       .withParameter("port", "49020")
                                       .withParameter("authentication",
                                                      newObjectValue()
                                                          .ofType(
                                                                  "org.mule.extension.http.api.request.authentication.BasicAuthentication")
                                                          .withParameter("username", "user")
                                                          .withParameter("password", "pass")
                                                          .build())
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup(CONNECTION)
                                       .withParameter("clientSocketProperties",
                                                      newObjectValue()
                                                          .withParameter("connectionTimeout", "1000")
                                                          .withParameter("keepAlive", "true")
                                                          .withParameter("receiveBufferSize", "1024")
                                                          .withParameter("sendBufferSize", "1024")
                                                          .withParameter("clientTimeout", "1000")
                                                          .withParameter("linger", "1000")
                                                          .build())
                                       .getDeclaration())
                                   .getDeclaration())
                               .getDeclaration())
        .withGlobalElement(
                           core.newConstruct("flow").withRefName("testFlow")
                               .withParameterGroup(newParameterGroup()
                                   .withParameter("initialState", "stopped")
                                   .getDeclaration())
                               .withComponent(http.newSource("listener")
                                   .withConfig("httpListener")
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("path", "testBuilder")
                                       .withParameter("redeliveryPolicy",
                                                      newObjectValue()
                                                          .withParameter("maxRedeliveryCount", "2")
                                                          .withParameter("useSecureHash", "true")
                                                          .build())
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup(CONNECTION)
                                       .withParameter("reconnectionStrategy",
                                                      newObjectValue()
                                                          .ofType("reconnect")
                                                          .withParameter("blocking", "true")
                                                          .withParameter("count", "1")
                                                          .withParameter("frequency", "0")
                                                          .build())
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup("Response")
                                       .withParameter("headers", "<![CDATA[#[{{'content-type' : 'text/plain'}}]]]>")
                                       .withParameter("body",
                                                      "<![CDATA[#[\n"
                                                          + "                    %dw 1.0\n"
                                                          + "                    output application/json\n"
                                                          + "                    input payload application/xml\n"
                                                          + "                    %var baseUrl=\"http://sample.cloudhub.io/api/v1.0/\"\n"
                                                          + "                    ---\n"
                                                          + "                    using (pageSize = payload.getItemsResponse.PageInfo.pageSize) {\n"
                                                          + "                         links: [\n"
                                                          + "                            {\n"
                                                          + "                                href: fullUrl,\n"
                                                          + "                                rel : \"self\"\n"
                                                          + "                            }\n"
                                                          + "                         ],\n"
                                                          + "                         collection: {\n"
                                                          + "                            size: pageSize,\n"
                                                          + "                            items: payload.getItemsResponse.*Item map {\n"
                                                          + "                                id: $.id,\n"
                                                          + "                                type: $.type,\n"
                                                          + "                                name: $.name\n"
                                                          + "                            }\n"
                                                          + "                         }\n"
                                                          + "                    }\n"
                                                          + "                ]]>")
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(core.newConstruct("choice")
                                   .withRoute(core.newRoute("when")
                                       .withParameterGroup(newParameterGroup()
                                           .withParameter("expression", "#[true]")
                                           .getDeclaration())
                                       .withComponent(db.newOperation("bulkInsert")
                                           .withParameterGroup(newParameterGroup("Query")
                                               .withParameter("sql",
                                                              "INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)")
                                               .withParameter("parameterTypes",
                                                              newListValue()
                                                                  .withValue(newObjectValue()
                                                                      .withParameter("key", "name")
                                                                      .withParameter("type", "VARCHAR")
                                                                      .build())
                                                                  .withValue(newObjectValue()
                                                                      .withParameter("key", "position")
                                                                      .withParameter("type", "INTEGER")
                                                                      .build())
                                                                  .build())
                                               .getDeclaration())
                                           .getDeclaration())
                                       .getDeclaration())
                                   .withRoute("otherwise", declarer -> declarer.withComponent(core.newConstruct("foreach")
                                       .withParameterGroup(newParameterGroup()
                                           .withParameter("collection", "#[myCollection]")
                                           .getDeclaration())
                                       .withComponent(core.newOperation("logger")
                                           .withParameterGroup(newParameterGroup()
                                               .withParameter("message", "#[payload]")
                                               .getDeclaration())
                                           .getDeclaration())
                                       .getDeclaration()))
                                   .getDeclaration())
                               .withComponent(db.newOperation("bulkInsert")
                                   .withParameterGroup(newParameterGroup("Query")
                                       .withParameter("sql", "INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)")
                                       .withParameter("parameterTypes",
                                                      newListValue()
                                                          .withValue(newObjectValue()
                                                              .withParameter("key", "name")
                                                              .withParameter("type", "VARCHAR").build())
                                                          .withValue(newObjectValue()
                                                              .withParameter("key", "position")
                                                              .withParameter("type", "INTEGER").build())
                                                          .build())
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(http.newOperation("request")
                                   .withConfig("httpRequester")
                                   .withParameterGroup(newParameterGroup("URI Settings")
                                       .withParameter("path", "/nested")
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("method", "POST")
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(db.newOperation("insert")
                                   .withConfig("dbConfig")
                                   .withParameterGroup(newParameterGroup("Query")
                                       .withParameter("sql",
                                                      "INSERT INTO PLANET(POSITION, NAME, DESCRIPTION) VALUES (777, 'Pluto', :description)")
                                       .withParameter("parameterTypes",
                                                      newListValue()
                                                          .withValue(newObjectValue()
                                                              .withParameter("key", "description")
                                                              .withParameter("type", "CLOB").build())
                                                          .build())
                                       .withParameter("inputParameters", "#[{{'description' : payload}}]")
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(
                                              sockets.newOperation("sendAndReceive")
                                                  .withParameterGroup(newParameterGroup()
                                                      .withParameter("target", "myVar")
                                                      .withParameter("streamingStrategy",
                                                                     newObjectValue()
                                                                         .ofType("repeatable-in-memory-stream")
                                                                         .withParameter("bufferSizeIncrement", "8")
                                                                         .withParameter("bufferUnit", "KB")
                                                                         .withParameter("initialBufferSize", "51")
                                                                         .withParameter("maxBufferSize", "1000")
                                                                         .build())
                                                      .getDeclaration())
                                                  .getDeclaration())
                               .withComponent(
                                              wsc.newOperation("consume")
                                                  .withParameterGroup(newParameterGroup()
                                                      .withParameter("operation", "GetCitiesByCountry")
                                                      .getDeclaration())
                                                  .withParameterGroup(newParameterGroup("Message")
                                                      .withParameter("attachments", "#[{}]")
                                                      .withParameter("headers",
                                                                     "#[{\"headers\": {con#headerIn: \"Header In Value\",con#headerInOut: \"Header In Out Value\"}]")
                                                      .withParameter("body", "#[payload]")
                                                      .getDeclaration())
                                                  .getDeclaration())
                               .getDeclaration())
        .getDeclaration();
  }
}
