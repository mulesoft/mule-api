/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.CONNECTION;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newListValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newObjectValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class ArtifactDeclarationJsonSerializerTestCase {

  private static final String EXPECTED_ARTIFACT_DECLARATION_JSON = "declaration/artifact-declaration.json";
  private ArtifactDeclaration applicationDeclaration;

  @Before
  public void setup() {
    applicationDeclaration = createArtifact();
  }

  @Test
  public void serializationTest() {
    JsonParser parser = new JsonParser();
    JsonReader reader = new JsonReader(new InputStreamReader(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(EXPECTED_ARTIFACT_DECLARATION_JSON)));
    JsonElement expected = parser.parse(reader);
    JsonElement json = parser.parse(ArtifactDeclarationJsonSerializer.getDefault(true).serialize(applicationDeclaration));
    assertThat(json, is(equalTo(expected)));
  }

  @Test
  public void serializeDeserializeTest() {
    ArtifactDeclarationJsonSerializer serializer = ArtifactDeclarationJsonSerializer.getDefault(true);
    String json = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration artifactDeclaration = serializer.deserialize(json);
    assertThat(json, applicationDeclaration, is(equalTo(artifactDeclaration)));
  }

  @Test
  @Ignore("See MULE-15599")
  public void serializeDeserializeSerializeTest() throws JSONException {
    ArtifactDeclarationJsonSerializer serializer = ArtifactDeclarationJsonSerializer.getDefault(true);
    String originalJson = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration loadedDeclaration = serializer.deserialize(originalJson);

    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(ArtifactDeclarationJsonSerializer.getDefault(true).serialize(loadedDeclaration));
    String generatedJson = jsonElement.toString();

    JSONAssert.assertEquals(originalJson, generatedJson, true);
  }

  private ArtifactDeclaration createArtifact() {

    ElementDeclarer core = ElementDeclarer.forExtension("mule");
    ElementDeclarer db = ElementDeclarer.forExtension("Database");
    ElementDeclarer http = ElementDeclarer.forExtension("HTTP");
    ElementDeclarer sockets = ElementDeclarer.forExtension("Sockets");
    ElementDeclarer wsc = ElementDeclarer.forExtension("Web Service Consumer");
    ElementDeclarer file = ElementDeclarer.forExtension("File");
    ElementDeclarer os = ElementDeclarer.forExtension("ObjectStore");

    return newArtifact()
        .withCustomParameter("xmlns:doc", "http://www.mulesoft.org/schema/mule/documentation")
        .withGlobalElement(core.newConstruct("configuration")
            .withParameterGroup(group -> group.withParameter("defaultErrorHandler-ref", "referableHandler"))
            .getDeclaration())
        .withGlobalElement(core.newConstruct("errorHandler")
            .withRefName("referableHandler")
            .withComponent(core.newRoute("onErrorContinue")
                .withParameterGroup(group -> group
                    .withParameter("type", "MULE:SOURCE_RESPONSE")
                    .withParameter("logException", "false")
                    .withParameter("enableNotifications", "false"))
                .withComponent(core.newOperation("logger")
                    .withParameterGroup(group -> group
                        .withParameter("level", "TRACE"))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(os.newGlobalParameter("objectStore")
            .withRefName("persistentStore")
            .withValue(newObjectValue()
                .ofType("org.mule.extension.objectstore.api.TopLevelObjectStore")
                .withParameter("entryTtl", "1")
                .withParameter("entryTtlUnit", "HOURS")
                .withParameter("maxEntries", "10")
                .withParameter("persistent", "true")
                .withParameter("expirationInterval", "2")
                .withParameter("expirationIntervalUnit", "HOURS")
                .withParameter("config-ref", "persistentConfig")
                .build())
            .getDeclaration())
        .withGlobalElement(os.newConfiguration("config")
            .withRefName("persistentConfig")
            .getDeclaration())
        .withGlobalElement(file.newConfiguration("config")
            .withRefName("fileConfig")
            .withConnection(file.newConnection("connection").getDeclaration())
            .getDeclaration())
        .withGlobalElement(wsc.newConfiguration("config")
            .withRefName("wscConfig")
            .withParameterGroup(newParameterGroup()
                .withParameter("expirationPolicy", newObjectValue()
                    .ofType("org.mule.runtime.extension.api.ExpirationPolicy")
                    .withParameter("maxIdleTime", "1")
                    .withParameter("timeUnit", MINUTES.name())
                    .build())
                .getDeclaration())
            .withConnection(wsc.newConnection("connection")
                .withParameterGroup(newParameterGroup()
                    .withParameter("soapVersion", "SOAP11")
                    .withParameter("mtomEnabled", "false")
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Connection")
                    .withParameter("wsdlLocation", "http://www.webservicex.com/globalweather.asmx?WSDL")
                    .withParameter("address", "http://www.webservicex.com/globalweather.asmx")
                    .withParameter("service", "GlobalWeather")
                    .withParameter("port", "GlobalWeatherSoap")
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(db.newConfiguration("config")
            .withRefName("dbConfig")
            .withConnection(db
                .newConnection("derby").withParameterGroup(newParameterGroup()
                    .withParameter("poolingProfile", newObjectValue()
                        .withParameter("maxPoolSize", "10")
                        .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("connectionProperties", newObjectValue()
                        .withParameter("first", "propertyOne")
                        .withParameter("second", "propertyTwo")
                        .build())
                    .withParameter("reconnection", newObjectValue()
                        .ofType("Reconnection")
                        .withParameter("failsDeployment", "true")
                        .withParameter("reconnectionStrategy", newObjectValue()
                            .ofType("reconnect")
                            .withParameter("count", "1")
                            .withParameter("frequency", "0")
                            .build())
                        .build())
                    .withParameter("database", "target/muleEmbeddedDB")
                    .withParameter("create", "true")
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("listenerConfig")
            .withRefName("httpListener")
            .withParameterGroup(newParameterGroup()
                .withParameter("basePath", "/")
                .getDeclaration())
            .withConnection(http.newConnection("listener")
                .withParameterGroup(newParameterGroup()
                    .withParameter("tlsContext", newObjectValue()
                        .withParameter("key-store", newObjectValue()
                            .withParameter("path", "ssltest-keystore.jks")
                            .withParameter("password", "changeit")
                            .withParameter("keyPassword", "changeit")
                            .build())
                        .withParameter("trust-store", newObjectValue()
                            .withParameter("insecure", "true")
                            .build())
                        .withParameter("revocation-check",
                                       newObjectValue()
                                           .ofType("standard-revocation-check")
                                           .withParameter("onlyEndEntities", "true")
                                           .build())
                        .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("host", "localhost")
                    .withParameter("port", "49019")
                    .withParameter("protocol", "HTTPS"))
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("requestConfig")
            .withRefName("httpRequester")
            .withConnection(http.newConnection("request")
                .withParameterGroup(group -> group.withParameter("authentication",
                                                                 newObjectValue()
                                                                     .ofType("org.mule.extension.http.api.request.authentication.BasicAuthentication")
                                                                     .withParameter("username", "user")
                                                                     .withParameter("password", "pass")
                                                                     .build()))
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("host", "localhost")
                    .withParameter("port", "49020")
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
        .withGlobalElement(core.newConstruct("flow")
            .withRefName("testFlow")
            .withCustomParameter("doc:id", "docUUID")
            .withParameterGroup(group -> group.withParameter("initialState", "stopped"))
            .withComponent(http.newSource("listener")
                .withConfig("httpListener")
                .withCustomParameter("doc:id", "docUUID")
                .withParameterGroup(newParameterGroup()
                    .withParameter("path", "testBuilder")
                    .withParameter("redeliveryPolicy",
                                   newObjectValue()
                                       .withParameter("maxRedeliveryCount", "2")
                                       .withParameter("useSecureHash", "true")
                                       .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("reconnectionStrategy",
                                   newObjectValue()
                                       .ofType("reconnect")
                                       .withParameter("count", "1")
                                       .withParameter("frequency", "0")
                                       .build()))
                .withParameterGroup(newParameterGroup("Response")
                    .withParameter("headers", "<![CDATA[#[{{'content-type' : 'text/plain'}}]]]>")
                    .withParameter("body",
                                   "<![CDATA[#[\n"
                                       + "                    %dw 2.0\n"
                                       + "                    output application/json\n"
                                       + "                    input payload application/xml\n"
                                       + "                    var baseUrl=\"http://sample.cloudhub.io/api/v1.0/\"\n"
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
                .withRoute(core.newRoute("otherwise")
                    .withComponent(core.newConstruct("foreach")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("collection", "#[myCollection]")
                            .getDeclaration())
                        .withComponent(core.newOperation("logger")
                            .withParameterGroup(newParameterGroup()
                                .withParameter("message", "#[payload]")
                                .getDeclaration())
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
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
            .withComponent(sockets.newOperation("sendAndReceive")
                .withParameterGroup(newParameterGroup()
                    .withParameter("streamingStrategy",
                                   newObjectValue()
                                       .ofType("repeatable-in-memory-stream")
                                       .withParameter("bufferSizeIncrement", "8")
                                       .withParameter("bufferUnit", "KB")
                                       .withParameter("initialBufferSize", "51")
                                       .withParameter("maxBufferSize", "1000")
                                       .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Output")
                    .withParameter("target", "myVar")
                    .withParameter("targetValue", "#[message]")
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newConstruct("try")
                .withComponent(wsc.newOperation("consume")
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
                .withComponent(core.newConstruct("errorHandler")
                    .withComponent(core.newRoute("onErrorContinue")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", "MULE:ANY")
                            .getDeclaration())
                        .withComponent(core.newOperation("logger").getDeclaration())
                        .getDeclaration())
                    .withComponent(core.newRoute("onErrorPropagate")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", "WSC:CONNECTIVITY")
                            .withParameter("when", "#[e.cause == null]")
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("schedulerFlow")
            .withComponent(core.newSource("scheduler")
                .withParameterGroup(newParameterGroup()
                    .withParameter("schedulingStrategy", newObjectValue()
                        .ofType("org.mule.runtime.core.api.source.scheduler.FixedFrequencyScheduler")
                        .withParameter("frequency", "50")
                        .withParameter("startDelay", "20")
                        .withParameter("timeUnit", "SECONDS")
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", "#[payload]").getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("cronSchedulerFlow")
            .withComponent(core.newSource("scheduler")
                .withParameterGroup(newParameterGroup()
                    .withParameter("schedulingStrategy", newObjectValue()
                        .ofType("org.mule.runtime.core.api.source.scheduler.CronScheduler")
                        .withParameter("expression", "0/1 * * * * ?")
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", "#[payload]").getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .getDeclaration();
  }
}
