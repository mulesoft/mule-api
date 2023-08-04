/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.test.meta.model;

import static org.mule.runtime.api.component.Component.NS_MULE_DOCUMENTATION;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.CONNECTION;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newListValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newObjectValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.cdata;
import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.plain;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.BOOLEAN;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.NUMBER;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.STRING;

import static java.util.concurrent.TimeUnit.MINUTES;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;

import org.mule.runtime.api.app.declaration.serialization.ArtifactDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;
import org.mule.runtime.app.declaration.api.fluent.SimpleValueType;

import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class ArtifactDeclarationJsonSerializerTestCase {

  private ArtifactDeclaration applicationDeclaration;

  @Before
  public void setup() {
    applicationDeclaration = createArtifact();
  }

  @Test
  public void serializationTest() {
    JsonParser parser = new JsonParser();
    JsonReader reader =
        new JsonReader(new InputStreamReader(getClass().getResourceAsStream(getExpectedArtifactDeclarationJson())));
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
  public void serializeDeserializeSerializeTest() throws JSONException {
    ArtifactDeclarationJsonSerializer serializer = ArtifactDeclarationJsonSerializer.getDefault(true);
    String originalJson = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration loadedDeclaration = serializer.deserialize(originalJson);

    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(ArtifactDeclarationJsonSerializer.getDefault(true).serialize(loadedDeclaration));
    String generatedJson = jsonElement.toString();

    JSONAssert.assertEquals(originalJson, generatedJson, true);
  }

  @Test
  public void equalsTopLevelParameterShouldIncludeParameters() {
    ElementDeclarer core = ElementDeclarer.forExtension("mule");
    ParameterObjectValue parameterObjectValue = ParameterObjectValue.builder()
        .withParameter("otherName", "simpleParam")
        .build();

    String globalParameterName = "globalParameterName";
    String globalTemplateRefName = "globalTemplateRefName";

    ParameterObjectValue modifiedParameterObjectValue = ParameterObjectValue.builder()
        .withParameter("otherName", "simpleParam")
        .withParameter("myCamelCaseName", "someContent")
        .build();

    assertThat(newArtifact().withGlobalElement(core.newGlobalParameter(globalParameterName)
        .withRefName(globalTemplateRefName)
        .withValue(parameterObjectValue)
        .getDeclaration()).getDeclaration(),
               not(equalTo(newArtifact().withGlobalElement(core.newGlobalParameter(globalParameterName)
                   .withRefName(globalTemplateRefName)
                   .withValue(modifiedParameterObjectValue)
                   .getDeclaration()).getDeclaration())));

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
        .withCustomParameter("xmlns:doc", NS_MULE_DOCUMENTATION)
        .withGlobalElement(core.newConstruct("configuration")
            .withParameterGroup(group -> group.withParameter("defaultErrorHandler-ref",
                                                             createStringParameter("referableHandler")))
            .getDeclaration())
        .withGlobalElement(core.newConstruct("errorHandler")
            .withRefName("referableHandler")
            .withComponent(core.newRoute("onErrorContinue")
                .withParameterGroup(group -> group
                    .withParameter("type", createStringParameter("MULE:SOURCE_RESPONSE"))
                    .withParameter("logException", createBooleanParameter("false"))
                    .withParameter("enableNotifications", createBooleanParameter("false")))
                .withComponent(core.newOperation("logger")
                    .withParameterGroup(group -> group
                        .withParameter("level", createStringParameter("TRACE")))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(os.newGlobalParameter("objectStore")
            .withRefName("persistentStore")
            .withValue(newObjectValue()
                .ofType("org.mule.extension.objectstore.api.TopLevelObjectStore")
                .withParameter("entryTtl", createNumberParameter("1"))
                .withParameter("entryTtlUnit", createStringParameter("HOURS"))
                .withParameter("maxEntries", createNumberParameter("10"))
                .withParameter("persistent", createBooleanParameter("true"))
                .withParameter("expirationInterval", createNumberParameter("2"))
                .withParameter("expirationIntervalUnit", createStringParameter("HOURS"))
                .withParameter("config-ref", createStringParameter("persistentConfig"))
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
                    .withParameter("maxIdleTime", createNumberParameter("1"))
                    .withParameter("timeUnit", createStringParameter(MINUTES.name()))
                    .build())
                .getDeclaration())
            .withConnection(wsc.newConnection("connection")
                .withParameterGroup(newParameterGroup()
                    .withParameter("soapVersion", createStringParameter("SOAP11"))
                    .withParameter("mtomEnabled", createBooleanParameter("false"))
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Connection")
                    .withParameter("wsdlLocation", createStringParameter("http://www.webservicex.com/globalweather.asmx?WSDL"))
                    .withParameter("address", createStringParameter("http://www.webservicex.com/globalweather.asmx"))
                    .withParameter("service", createStringParameter("GlobalWeather"))
                    .withParameter("port", createStringParameter("GlobalWeatherSoap"))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(db.newConfiguration("config")
            .withRefName("dbConfig")
            .withConnection(db
                .newConnection("derby").withParameterGroup(newParameterGroup()
                    .withParameter("poolingProfile", newObjectValue()
                        .withParameter("maxPoolSize", createNumberParameter("10"))
                        .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("connectionProperties", newObjectValue()
                        .withParameter("first", createStringParameter("propertyOne"))
                        .withParameter("second", createStringParameter("propertyTwo"))
                        .build())
                    .withParameter("reconnection", newObjectValue()
                        .ofType("Reconnection")
                        .withParameter("failsDeployment", createBooleanParameter("true"))
                        .withParameter("reconnectionStrategy", newObjectValue()
                            .ofType("reconnect")
                            .withParameter("count", createNumberParameter("1"))
                            .withParameter("frequency", createNumberParameter("0"))
                            .build())
                        .build())
                    .withParameter("database", createStringParameter("target/muleEmbeddedDB"))
                    .withParameter("create", createBooleanParameter("true"))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("listenerConfig")
            .withRefName("httpListener")
            .withParameterGroup(newParameterGroup()
                .withParameter("basePath", createStringParameter("/"))
                .getDeclaration())
            .withConnection(http.newConnection("listener")
                .withParameterGroup(newParameterGroup()
                    .withParameter("tlsContext", newObjectValue()
                        .withParameter("key-store", newObjectValue()
                            .withParameter("path", createStringParameter("ssltest-keystore.jks"))
                            .withParameter("password", createStringParameter("changeit"))
                            .withParameter("keyPassword", createStringParameter("changeit"))
                            .build())
                        .withParameter("trust-store", newObjectValue()
                            .withParameter("insecure", createBooleanParameter("true"))
                            .build())
                        .withParameter("revocation-check",
                                       newObjectValue()
                                           .ofType("standard-revocation-check")
                                           .withParameter("onlyEndEntities", createBooleanParameter("true"))
                                           .build())
                        .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("host", createStringParameter("localhost"))
                    .withParameter("port", createNumberParameter("49019"))
                    .withParameter("protocol", createStringParameter("HTTPS")))
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("requestConfig")
            .withRefName("httpRequester")
            .withConnection(http.newConnection("request")
                .withParameterGroup(group -> group.withParameter("authentication",
                                                                 newObjectValue()
                                                                     .ofType("org.mule.extension.http.api.request.authentication.BasicAuthentication")
                                                                     .withParameter("username", createStringParameter("user"))
                                                                     .withParameter("password", createStringParameter("pass"))
                                                                     .build()))
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("host", createStringParameter("localhost"))
                    .withParameter("port", createNumberParameter("49020"))
                    .withParameter("clientSocketProperties",
                                   newObjectValue()
                                       .withParameter("connectionTimeout", createNumberParameter("1000"))
                                       .withParameter("keepAlive", createBooleanParameter("true"))
                                       .withParameter("receiveBufferSize", createNumberParameter("1024"))
                                       .withParameter("sendBufferSize", createNumberParameter("1024"))
                                       .withParameter("clientTimeout", createNumberParameter("1000"))
                                       .withParameter("linger", createNumberParameter("1000"))
                                       .build())
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow")
            .withRefName("testFlow")
            .withCustomParameter("doc:id", "docUUID")
            .withParameterGroup(group -> group.withParameter("initialState", createStringParameter("stopped")))
            .withComponent(http.newSource("listener")
                .withConfig("httpListener")
                .withCustomParameter("doc:id", "docUUID")
                .withParameterGroup(newParameterGroup()
                    .withParameter("path", createStringParameter("testBuilder"))
                    .withParameter("redeliveryPolicy",
                                   newObjectValue()
                                       .withParameter("maxRedeliveryCount", createNumberParameter("2"))
                                       .withParameter("useSecureHash", createBooleanParameter("true"))
                                       .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("reconnectionStrategy",
                                   newObjectValue()
                                       .ofType("reconnect")
                                       .withParameter("count", createNumberParameter("1"))
                                       .withParameter("frequency", createNumberParameter("0"))
                                       .build()))
                .withParameterGroup(newParameterGroup("Response")
                    .withParameter("headers", createStringCdataParameter("<![CDATA[#[{{'content-type' : 'text/plain'}}]]]>"))
                    .withParameter("body", createStringCdataParameter(
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
                                                                          + "                ]]>"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newConstruct("choice")
                .withRoute(core.newRoute("when")
                    .withParameterGroup(newParameterGroup()
                        .withParameter("expression", createStringParameter("#[true]"))
                        .getDeclaration())
                    .withComponent(db.newOperation("bulkInsert")
                        .withParameterGroup(newParameterGroup("Query")
                            .withParameter("sql",
                                           createStringParameter("INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)"))
                            .withParameter("parameterTypes",
                                           newListValue()
                                               .withValue(newObjectValue()
                                                   .withParameter("key", createStringParameter("name"))
                                                   .withParameter("type", createStringParameter("VARCHAR"))
                                                   .build())
                                               .withValue(newObjectValue()
                                                   .withParameter("key", createNumberParameter("position"))
                                                   .withParameter("type", createStringParameter("INTEGER"))
                                                   .build())
                                               .build())
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .withRoute(core.newRoute("otherwise")
                    .withComponent(core.newConstruct("foreach")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("collection", createStringParameter("#[myCollection]"))
                            .getDeclaration())
                        .withComponent(core.newOperation("logger")
                            .withParameterGroup(newParameterGroup()
                                .withParameter("message", createStringParameter("#[payload]"))
                                .getDeclaration())
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(db.newOperation("bulkInsert")
                .withParameterGroup(newParameterGroup("Query")
                    .withParameter("sql", createStringParameter("INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)"))
                    .withParameter("parameterTypes",
                                   newListValue()
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("name"))
                                           .withParameter("type", createStringParameter("VARCHAR")).build())
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("position"))
                                           .withParameter("type", createStringParameter("INTEGER")).build())
                                       .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(http.newOperation("request")
                .withConfig("httpRequester")
                .withParameterGroup(newParameterGroup("URI Settings")
                    .withParameter("path", createStringParameter("/nested"))
                    .getDeclaration())
                .withParameterGroup(newParameterGroup()
                    .withParameter("method", createStringParameter("POST"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(db.newOperation("insert")
                .withConfig("dbConfig")
                .withParameterGroup(newParameterGroup("Query")
                    .withParameter("sql",
                                   createStringParameter("INSERT INTO PLANET(POSITION, NAME, DESCRIPTION) VALUES (777, 'Pluto', :description)"))
                    .withParameter("parameterTypes",
                                   newListValue()
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("description"))
                                           .withParameter("type", createStringParameter("CLOB")).build())
                                       .build())
                    .withParameter("inputParameters", createStringParameter("#[{{'description' : payload}}]"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(sockets.newOperation("sendAndReceive")
                .withParameterGroup(newParameterGroup()
                    .withParameter("streamingStrategy",
                                   newObjectValue()
                                       .ofType("repeatable-in-memory-stream")
                                       .withParameter("bufferSizeIncrement", createNumberParameter("8"))
                                       .withParameter("bufferUnit", createStringParameter("KB"))
                                       .withParameter("initialBufferSize", createNumberParameter("51"))
                                       .withParameter("maxBufferSize", createNumberParameter("1000"))
                                       .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Output")
                    .withParameter("target", createStringParameter("myVar"))
                    .withParameter("targetValue", createStringParameter("#[message]"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newConstruct("try")
                .withComponent(wsc.newOperation("consume")
                    .withParameterGroup(newParameterGroup()
                        .withParameter("operation", createStringParameter("GetCitiesByCountry"))
                        .getDeclaration())
                    .withParameterGroup(newParameterGroup("Message")
                        .withParameter("attachments", createStringParameter("#[{}]"))
                        .withParameter("headers",
                                       createStringParameter("#[{\"headers\": {con#headerIn: \"Header In Value\",con#headerInOut: \"Header In Out Value\"}]"))
                        .withParameter("body", createStringParameter("#[payload]"))
                        .getDeclaration())
                    .getDeclaration())
                .withComponent(core.newConstruct("errorHandler")
                    .withComponent(core.newRoute("onErrorContinue")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", createStringParameter("MULE:ANY"))
                            .getDeclaration())
                        .withComponent(core.newOperation("logger").getDeclaration())
                        .getDeclaration())
                    .withComponent(core.newRoute("onErrorPropagate")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", createStringParameter("WSC:CONNECTIVITY"))
                            .withParameter("when", createStringParameter("#[e.cause == null]"))
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
                        .withParameter("frequency", createNumberParameter("50"))
                        .withParameter("startDelay", createNumberParameter("20"))
                        .withParameter("timeUnit", createStringParameter("SECONDS"))
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", createStringParameter("#[payload]")).getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("cronSchedulerFlow")
            .withComponent(core.newSource("scheduler")
                .withParameterGroup(newParameterGroup()
                    .withParameter("schedulingStrategy", newObjectValue()
                        .ofType("org.mule.runtime.core.api.source.scheduler.CronScheduler")
                        .withParameter("expression", createStringParameter("0/1 * * * * ?"))
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", createStringParameter("#[payload]")).getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .getDeclaration();
  }

  protected String getExpectedArtifactDeclarationJson() {
    return "/declaration/artifact-declaration.json";
  }

  private ParameterValue createNumberParameter(String value) {
    return createPlainParameter(value, NUMBER);
  }

  private ParameterValue createBooleanParameter(String value) {
    return createPlainParameter(value, BOOLEAN);
  }

  private ParameterValue createStringParameter(String value) {
    SimpleValueType type = STRING;
    return createPlainParameter(value, type);
  }

  private ParameterValue createStringCdataParameter(String value) {
    return createCdataParameter(value, STRING);
  }

  private ParameterValue createPlainParameter(String value, SimpleValueType type) {
    return plain(value, type);
  }

  private ParameterValue createCdataParameter(String value, SimpleValueType type) {
    return cdata(value, type);
  }
}
