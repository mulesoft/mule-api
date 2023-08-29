/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newObjectValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.CONNECTION;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.ConfigurationElementDeclaration;
import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;
import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.component.location.Location;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class ElementDeclarationLocationPathTestCase {

  private ArtifactDeclaration multiFlowDeclaration;

  @Before
  public void setup() throws Exception {
    multiFlowDeclaration = createMultiFlowArtifactDeclaration();
  }

  @Test
  public void globalConfig() throws Exception {
    Location location = Location.builder().globalName("config").build();
    Optional<ConfigurationElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getRefName(), is("config"));
  }

  @Test
  public void getConnection() throws Exception {
    Location location = Location.builder().globalName("config").addConnectionPart().build();
    Optional<ConnectionElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("active-mq-connection"));
  }

  @Test
  public void globalFlow() throws Exception {
    Location location = Location.builder().globalName("bridge").build();
    assertThat(multiFlowDeclaration.findElement(location).isPresent(), is(true));
  }

  @Test
  public void messageProcessor() throws Exception {
    Location location = Location.builder().globalName("send-payload").addProcessorsPart().addIndexPart(0).build();
    Optional<? extends ElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("publish"));
  }

  @Test
  public void scope() throws Exception {
    Location location = Location.builder().globalName("bridge").addProcessorsPart().addIndexPart(1).build();
    Optional<? extends ElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("foreach"));
  }

  @Test
  public void messageProcessorInScope() throws Exception {
    Location location = Location.builder().globalName("bridge")
        .addProcessorsPart().addIndexPart(1)
        .addProcessorsPart().addIndexPart(1)
        .build();
    Optional<? extends ElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("logger"));
  }

  @Test
  public void messageSource() throws Exception {
    Location location = Location.builder().globalName("testFlow").addSourcePart().build();
    Optional<? extends ElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("listener"));
  }

  @Test
  public void messageProcessorParameter() throws Exception {
    Location location = Location.builder().globalName("bridge")
        .addProcessorsPart().addIndexPart(0)
        .addParameterPart().addPart("destination")
        .build();

    Optional<? extends ElementDeclaration> element = multiFlowDeclaration.findElement(location);
    assertThat(element.isPresent(), is(true));
    assertThat(element.get().getName(), is("destination"));
  }

  private static ArtifactDeclaration createMultiFlowArtifactDeclaration() {
    ElementDeclarer jms = ElementDeclarer.forExtension("JMS");
    ElementDeclarer core = ElementDeclarer.forExtension("Mule Core");
    ElementDeclarer http = ElementDeclarer.forExtension("HTTP");

    return newArtifact().withGlobalElement(jms.newConfiguration("config")
        .withRefName("config")
        .withConnection(jms.newConnection("active-mq-connection")
            .withParameterGroup(newParameterGroup()
                .withParameter("cachingStrategy",
                               newObjectValue()
                                   .ofType("org.mule.extensions.jms.api.connection.caching.NoCachingConfiguration")
                                   .build())
                .getDeclaration())
            .getDeclaration())
        .getDeclaration())
        .withGlobalElement(
                           core.newConstruct("flow")
                               .withRefName("send-payload")
                               .withComponent(jms.newOperation("publish")
                                   .withConfig("config")
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("destination", "#[initialDestination]")
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup("Message")
                                       .withParameter("body", "#[payload]")
                                       .withParameter("properties", "#[{(initialProperty): propertyValue}]")
                                       .getDeclaration())
                                   .getDeclaration())
                               .getDeclaration())
        .withGlobalElement(
                           core.newConstruct("flow").withRefName("bridge")
                               .withComponent(jms.newOperation("consume")
                                   .withConfig("config")
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("destination", "#[initialDestination]")
                                       .withParameter("maximumWait", "1000")
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(
                                              core.newConstruct("foreach")
                                                  .withComponent(jms.newOperation("publish")
                                                      .withConfig("config")
                                                      .withParameterGroup(newParameterGroup()
                                                          .withParameter("destination", "#[finalDestination]")
                                                          .getDeclaration())
                                                      .withParameterGroup(newParameterGroup("Message")
                                                          .withParameter("jmsxProperties",
                                                                         "#[attributes.properties.jmsxProperties]")
                                                          .withParameter("body",
                                                                         "#[bridgePrefix ++ payload]")
                                                          .withParameter("properties",
                                                                         "#[attributes.properties.userProperties]")
                                                          .getDeclaration())
                                                      .getDeclaration())
                                                  .withComponent(core.newOperation("logger")
                                                      .withParameterGroup(newParameterGroup()
                                                          .withParameter("message", "Message Sent")
                                                          .getDeclaration())
                                                      .getDeclaration())
                                                  .getDeclaration())
                               .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("bridge-receiver")
            .withComponent(jms.newOperation("consume")
                .withConfig("config")
                .withParameterGroup(newParameterGroup()
                    .withParameter("destination", "#[finalDestination]")
                    .withParameter("maximumWait", "1000")
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
                           core.newConstruct("flow")
                               .withRefName("testFlow")
                               .withParameterGroup(newParameterGroup()
                                   .withParameter("initialState", "stopped")
                                   .getDeclaration())
                               .withComponent(http.newSource("listener")
                                   .withConfig("httpListener")
                                   .withParameterGroup(newParameterGroup()
                                       .withParameter("path", "testBuilder")
                                       .getDeclaration())
                                   .withParameterGroup(newParameterGroup("Response")
                                       .withParameter("headers", "#[{{'content-type' : 'text/plain'}}]")
                                       .withParameter("body", "#[{'my': 'map'}]")
                                       .getDeclaration())
                                   .getDeclaration())
                               .withComponent(
                                              core.newConstruct("choice")
                                                  .withRoute(core.newRoute("when")
                                                      .withParameterGroup(newParameterGroup()
                                                          .withParameter("expression", "#[true]")
                                                          .getDeclaration())
                                                      .withComponent(jms.newOperation("publish")
                                                          .withConfig("config")
                                                          .withParameterGroup(newParameterGroup()
                                                              .withParameter("destination", "#[finalDestination]")
                                                              .getDeclaration())
                                                          .withParameterGroup(newParameterGroup("Message")
                                                              .withParameter("body",
                                                                             "#[bridgePrefix ++ payload]")
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
                               .getDeclaration())
        .getDeclaration();
  }
}
