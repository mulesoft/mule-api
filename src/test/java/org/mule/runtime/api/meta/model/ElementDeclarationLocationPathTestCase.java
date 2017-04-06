/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newFlow;
import static org.mule.runtime.api.app.declaration.fluent.ElementDeclarer.newObjectValue;
import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.ConnectionElementDeclaration;
import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.fluent.ElementDeclarer;
import org.mule.runtime.api.component.location.Location;

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
            .withParameter("disableValidation", "true")
            .withParameter("cachingStrategy",
                           newObjectValue()
                               .ofType(
                                       "org.mule.extensions.jms.api.connection.caching.NoCachingConfiguration")
                               .build())
            .getDeclaration())
        .getDeclaration())
        .withGlobalElement(newFlow().withRefName("send-payload")
            .withComponent(jms.newOperation("publish")
                .withConfig("config")
                .withParameter("destination", "#[initialDestination]")
                .withParameter("messageBuilder",
                               newObjectValue()
                                   .withParameter("body", "#[payload]")
                                   .withParameter("properties", "#[{(initialProperty): propertyValue}]")
                                   .build())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(newFlow().withRefName("bridge")
            .withComponent(jms.newOperation("consume")
                .withConfig("config")
                .withParameter("destination", "#[initialDestination]")
                .withParameter("maximumWait", "1000")
                .getDeclaration())
            .withComponent(core.newScope("foreach")
                .withComponent(jms.newOperation("publish")
                    .withConfig("config")
                    .withParameter("destination", "#[finalDestination]")
                    .withParameter("messageBuilder",
                                   newObjectValue()
                                       .withParameter("jmsxProperties",
                                                      "#[attributes.properties.jmsxProperties]")
                                       .withParameter("body",
                                                      "#[bridgePrefix ++ payload]")
                                       .withParameter("properties",
                                                      "#[attributes.properties.userProperties]")
                                       .build())
                    .getDeclaration())
                .withComponent(core.newOperation("logger")
                    .withParameter("message", "Message Sent")
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(newFlow().withRefName("bridge-receiver")
            .withComponent(jms.newOperation("consume").withConfig("config")
                .withParameter("destination", "#[finalDestination]")
                .withParameter("maximumWait", "1000")
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
        .withGlobalElement(newFlow().withRefName("testFlow")
            .withParameter("initialState", "stopped")
            .withComponent(
                           http.newSource("listener")
                               .withConfig("httpListener")
                               .withParameter("path", "testBuilder")
                               .withParameter("response",
                                              newObjectValue()
                                                  .withParameter("headers", "#[{{'content-type' : 'text/plain'}}]")
                                                  .withParameter("body", "#[{'my': 'map'}]")
                                                  .build())
                               .getDeclaration())
            .withComponent(
                           core.newRouter("choice")
                               .withRoute(core.newRoute("when")
                                   .withParameter("expression", "#[true]")
                                   .withComponent(jms.newOperation("publish")
                                       .withConfig("config")
                                       .withParameter("destination", "#[finalDestination]")
                                       .withParameter("messageBuilder",
                                                      newObjectValue()
                                                          .withParameter("body", "#[bridgePrefix ++ payload]")
                                                          .build())
                                       .getDeclaration())
                                   .getDeclaration())
                               .withRoute(core.newRoute("otherwise")
                                   .withComponent(core.newScope("foreach")
                                       .withParameter("collection", "#[myCollection]")
                                       .withComponent(core.newOperation("logger")
                                           .withParameter("message", "#[payload]")
                                           .getDeclaration())
                                       .getDeclaration())
                                   .getDeclaration())
                               .getDeclaration())
            .getDeclaration())
        .getDeclaration();
  }
}
