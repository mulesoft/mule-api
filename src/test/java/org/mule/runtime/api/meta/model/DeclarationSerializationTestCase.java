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
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newFlow;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newListValue;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newObjectValue;
import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ElementDeclarer;
import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class DeclarationSerializationTestCase {

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

    return newArtifact()
        .withGlobalElement(db.newGlobalParameter("query")
            .withRefName("selectQuery")
            .withValue(newObjectValue()
                .ofType("org.mule.extension.db.api.param.QueryDefinition")
                .withParameter("sql", "select * from PLANET where name = :name")
                .withParameter("inputParameters", "#[mel:['name' : payload]]")
                .build())
            .getDeclaration())
        .withGlobalElement(db.newConfiguration("config")
            .withRefName("dbConfig")
            .withConnection(db.newConnection("derby-connection")
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
        .withGlobalElement(http.newConfiguration("listener-config")
            .withRefName("httpListener")
            .withParameter("basePath", "/")
            .withConnection(http.newConnection("listener-connection")
                .withParameter("disableValidation", "true")
                .withParameter("tlsContext", newObjectValue()
                    .withParameter("key-store", newObjectValue()
                        .withParameter("path", "ssltest-keystore.jks")
                        .withParameter("password", "changeit")
                        .withParameter("keyPassword", "changeit")
                        .build())
                    .build())
                .withParameter("host", "localhost")
                .withParameter("port", "49019")
                .withParameter("protocol", "HTTPS")
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("request-config")
            .withRefName("httpRequester")
            .withConnection(http.newConnection("request-connection")
                .withParameter("host", "localhost")
                .withParameter("port", "49020")
                .withParameter("authentication",
                               newObjectValue()
                                   .ofType(
                                           "org.mule.extension.http.api.request.authentication.BasicAuthentication")
                                   .withParameter("username", "user")
                                   .withParameter("password", "pass")
                                   .build())
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
        .withGlobalElement(newFlow("testFlow")
            .withParameter("initialState", "stopped")
            .withComponent(http.newSource("listener")
                .withConfig("httpListener")
                .withParameter("path", "testBuilder")
                .withParameter("redeliveryPolicy",
                               newObjectValue()
                                   .withParameter("maxRedeliveryCount", "2")
                                   .withParameter("secureHash", "true")
                                   .build())
                .withParameter("reconnectionStrategy",
                               newObjectValue()
                                   .ofType("reconnect")
                                   .withParameter("blocking", "true")
                                   .withParameter("count", "1")
                                   .withParameter("frequency", "0")
                                   .build())
                .withParameter("response",
                               newObjectValue()
                                   .withParameter("headers", "#[{{'content-type' : 'text/plain'}}]")
                                   .build())
                .getDeclaration())
            .withComponent(core.newRouter("choice")
                .withRoute(core.newRoute("when")
                    .withParameter("expression", "#[true]")
                    .withComponent(db.newOperation("bulkInsert")
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
                .withRoute(core.newRoute("otherwise")
                    .withComponent(core.newOperation("logger")
                        .withParameter("message", "#[payload]")
                        .getDeclaration())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(db.newOperation("bulkInsert")
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
            .withComponent(http.newOperation("request")
                .withConfig("httpRequester")
                .withParameter("path", "/nested")
                .withParameter("method", "POST")
                .getDeclaration())
            .withComponent(db.newOperation("insert")
                .withConfig("dbConfig")
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
            .withComponent(sockets.newOperation("sendAndReceive")
                .withParameter("target", "myVar")
                .withParameter("streamingStrategy",
                               newObjectValue()
                                   .ofType("repeatable-in-memory-stream")
                                   .withParameter("bufferSizeIncrement", "8")
                                   .withParameter("bufferUnit", "KB")
                                   .withParameter("initialBufferSize", "51")
                                   .withParameter("maxInMemorySize", "1000")
                                   .build())
                .getDeclaration())
            .getDeclaration())
        .getDeclaration();
  }
}
