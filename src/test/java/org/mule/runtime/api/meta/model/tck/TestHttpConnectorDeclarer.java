/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.tck;

import static org.mockito.Mockito.mock;
import static org.mule.runtime.api.meta.Category.COMMUNITY;
import static org.mule.runtime.api.meta.model.connection.ConnectionManagementType.NONE;
import org.mule.metadata.api.annotation.TypeIdAnnotation;
import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclarer;

import java.io.Serializable;


/**
 * A simple pojo containing reference information for making test around a {@link ExtensionDeclarer}
 * which represents a theoretical &quot;Http connector&quot; extension.
 * <p>
 * It contains an actual {@link ExtensionDeclarer} that can be accessed through the {@link #getExtensionDeclarer()}
 * method plus some other getters which provides access to other declaration components
 * that you might want to make tests against.
 * <p>
 * This case focuses on the scenario in which each config has its own set of operations, providers and sources.
 *
 * @since 1.0
 */
public class TestHttpConnectorDeclarer extends BaseDeclarerTestCase {

  public static final String EXTENSION_NAME = "http";
  public static final String EXTENSION_DESCRIPTION = "Http Connector";
  public static final String VENDOR = "Mulesoft";
  public static final String REQUESTER_CONFIG_NAME = "requester";
  public static final String REQUESTER_CONFIG_DESCRIPTION = "http requester";
  public static final String REQUEST_OPERATION_NAME = "request";
  public static final String PATH = "path";
  public static final String REQUESTER_PROVIDER = "requesterProvider";
  public static final String LISTENER_CONFIG_NAME = "listener";
  public static final String LISTENER_CONFIG_DESCRIPTION = "http listener";
  public static final String LISTEN_MESSAGE_SOURCE = "listen";
  public static final String PORT = "port";
  public static final String PARAMETER_GROUP = "parameters";
  public static final int DEFAULT_PORT = 8080;
  public static final String VERSION = "1.0";
  public static final String STATIC_RESOURCE_OPERATION_NAME = "staticResource";
  public static final MuleVersion MIN_MULE_VERSION = new MuleVersion("4.0");
  public static final ObjectType COMPLEX_TYPE = mock(ObjectType.class);
  public static final ObjectType ANOTHER_COMPLEX_TYPE = mock(ObjectType.class);

  private final ExtensionDeclarer extensionDeclarer = new ExtensionDeclarer();

  public TestHttpConnectorDeclarer() {
    extensionDeclarer.named(EXTENSION_NAME)
        .describedAs(EXTENSION_DESCRIPTION)
        .fromVendor(VENDOR)
        .onVersion(VERSION)
        .withCategory(COMMUNITY)
        .withMinMuleVersion(MIN_MULE_VERSION)
        .withType(COMPLEX_TYPE)
        .withType(ANOTHER_COMPLEX_TYPE)
        .withXmlDsl(XmlDslModel.builder().build());
    OperationDeclarer staticResource = extensionDeclarer.withOperation(STATIC_RESOURCE_OPERATION_NAME);
    staticResource.withOutput().ofType(getBinaryType());
    staticResource.withParameterGroup(PARAMETER_GROUP).withRequiredParameter(PATH).ofType(getStringType());

    ConfigurationDeclarer requesterConfig =
        extensionDeclarer.withConfig(REQUESTER_CONFIG_NAME).describedAs(REQUESTER_CONFIG_DESCRIPTION);
    OperationDeclarer request = requesterConfig.withOperation(REQUEST_OPERATION_NAME);
    request.withOutput().ofType(getBinaryType());
    request.withParameterGroup(PARAMETER_GROUP).withRequiredParameter(PATH).ofType(getStringType());

    requesterConfig.withConnectionProvider(REQUESTER_PROVIDER).withConnectionManagementType(NONE);

    ConfigurationDeclarer listenerRequester = extensionDeclarer.withConfig(LISTENER_CONFIG_NAME)
        .describedAs(LISTENER_CONFIG_DESCRIPTION);
    SourceDeclarer listen = listenerRequester.withMessageSource(LISTEN_MESSAGE_SOURCE);
    listen.withOutput().ofType(getBinaryType());
    listen.withOutputAttributes()
        .ofType(typeBuilder.objectType().with(new TypeIdAnnotation(Serializable.class.getName())).build());
    listen.withParameterGroup(PARAMETER_GROUP).withOptionalParameter(PORT).ofType(getNumberType()).defaultingTo(DEFAULT_PORT);
  }

  public ExtensionDeclarer getExtensionDeclarer() {
    return extensionDeclarer;
  }
}
