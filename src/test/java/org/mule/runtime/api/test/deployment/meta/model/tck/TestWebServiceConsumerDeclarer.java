/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.deployment.meta.model.tck;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import static org.mule.runtime.api.meta.ExpressionSupport.REQUIRED;
import static org.mule.runtime.api.meta.ExternalLibraryType.JAR;
import static org.mule.runtime.api.meta.model.connection.ConnectionManagementType.NONE;
import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;
import org.mule.runtime.api.meta.Category;
import org.mule.runtime.api.meta.model.ExternalLibraryModel;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.declaration.fluent.ConfigurationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ConnectionProviderDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.FunctionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ParameterGroupDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.SourceDeclarer;
import org.mule.runtime.api.meta.model.operation.OperationModel;

import java.io.Serializable;

/**
 * A simple pojo containing reference information for making test around a {@link ExtensionDeclarer} which represents a
 * theoretical &quot;Web Service Consumer&quot; extension.
 * <p>
 * It contains an actual {@link ExtensionDeclarer} that can be accessed through the {@link #getExtensionDeclarer()} method plus
 * some other getters which provides access to other declaration components that you might want to make tests against.
 * <p>
 * This case focuses on the scenario in which all sources, providers and operations are available on all configs
 *
 * @since 1.0
 */
public class TestWebServiceConsumerDeclarer extends TestBaseDeclarer {

  public static final String CONFIG_NAME = "config";
  public static final String CONFIG_DESCRIPTION = "Default description";
  public static final String WS_CONSUMER = "WSConsumer";
  public static final String WS_CONSUMER_DESCRIPTION = "Generic Consumer for SOAP Web Services";
  public static final String VERSION = "3.6.0";
  public static final String WSDL_LOCATION = "wsdlLocation";
  public static final String URI_TO_FIND_THE_WSDL = "URI to find the WSDL";
  public static final String SERVICE = "service";
  public static final String SERVICE_NAME = "Service Name";
  public static final String PORT = "port";
  public static final String SERVICE_PORT = "Service Port";
  public static final String ADDRESS = "address";
  public static final String SERVICE_ADDRESS = "Service address";
  public static final String CONSUMER = "consumer";
  public static final String GO_GET_THEM_TIGER = "Go get them tiger";
  public static final String OPERATION = "operation";
  public static final String COLLECTION_PARAMETER = "things";
  public static final String THE_OPERATION_TO_USE = "The operation to use";
  public static final String MTOM_ENABLED = "mtomEnabled";
  public static final String MTOM_DESCRIPTION = "Whether or not use MTOM for attachments";
  public static final String BROADCAST = "broadcast";
  public static final String BROADCAST_DESCRIPTION = "consumes many services";
  public static final String CALLBACK = "callback";
  public static final String CALLBACK_DESCRIPTION = "async callback";
  public static final String HAS_NO_ARGS = "has no args";
  public static final String ARG_LESS = "argLess";
  public static final String USERNAME = "username";
  public static final String USERNAME_DESCRIPTION = "Authentication username";
  public static final String PASSWORD = "password";
  public static final String PASSWORD_DESCRIPTION = "Authentication password";
  public static final String MULESOFT = "MuleSoft";
  public static final String LISTENER = "listener";
  public static final String FUNCTION_NAME = "function";
  public static final String FUNCTION_DESCRIPTION = "sample function";
  public static final String LISTEN_DESCRIPTION = "Listen requests";
  public static final String URL = "url";
  public static final String URL_DESCRIPTION = "Url to listen on";
  public static final String PORT_DESCRIPTION = "Port to listen on";
  public static final String CONFIG_PARAMETER_GROUP = "Config parameters";
  public static final String OPERATION_PARAMETER_GROUP = "Operation parameters";
  public static final String CONNECTION_PROVIDER_PARAMETER_GROUP = "Connection Provider parameters";
  public static final String SOURCE_PARAMETER_GROUP = "Source parameters";
  public static final String DSL_PREFIX = "wsc";

  public static final int DEFAULT_PORT = 8080;

  public static final ModelProperty EXTENSION_MODEL_PROPERTY = new TestModelProperty("customExtensionModelProperty");
  public static final ModelProperty CONFIGURATION_MODEL_PROPERTY = new TestModelProperty("customConfigurationModelProperty");
  public static final ModelProperty OPERATION_MODEL_PROPERTY = new TestModelProperty("customOperationModelProperty");
  public static final ModelProperty PARAMETER_MODEL_PROPERTY = new TestModelProperty("customParameterModelProperty");
  public static final ModelProperty SOURCE_MODEL_PROPERTY = new TestModelProperty("customSourceModelProperty");

  public static final String CONNECTION_PROVIDER_NAME = "connectionProvider";
  public static final String CONNECTION_PROVIDER_DESCRIPTION = "my connection provider";

  public static ExternalLibraryModel EXTERNAL_LIBRARY_MODEL = ExternalLibraryModel.builder()
      .withName("wss")
      .withDescription("WSS native library")
      .withRegexpMatcher("*.jar")
      .withRequiredClassName("org.my.Library")
      .withType(JAR)
      .build();


  private final ExtensionDeclarer extensionDeclarer;

  public TestWebServiceConsumerDeclarer() {
    extensionDeclarer = declareOn(new ExtensionDeclarer());
  }

  public ExtensionDeclarer declareOn(ExtensionDeclarer extensionDeclarer) {
    extensionDeclarer.named(WS_CONSUMER)
        .describedAs(WS_CONSUMER_DESCRIPTION)
        .onVersion(VERSION)
        .fromVendor(MULESOFT)
        .withCategory(Category.SELECT)
        .withModelProperty(EXTENSION_MODEL_PROPERTY)
        .withXmlDsl(XmlDslModel.builder().setPrefix(DSL_PREFIX).build())
        .withExternalLibrary(EXTERNAL_LIBRARY_MODEL);

    ConfigurationDeclarer config =
        extensionDeclarer.withConfig(CONFIG_NAME)
            .describedAs(CONFIG_DESCRIPTION)
            .withExternalLibrary(EXTERNAL_LIBRARY_MODEL)
            .withModelProperty(CONFIGURATION_MODEL_PROPERTY);

    ParameterGroupDeclarer parameterGroup = config.onParameterGroup(CONFIG_PARAMETER_GROUP);
    parameterGroup.withRequiredParameter(ADDRESS).describedAs(SERVICE_ADDRESS).ofType(getStringType());
    parameterGroup.withRequiredParameter(PORT).describedAs(SERVICE_PORT).ofType(getStringType());
    parameterGroup.withRequiredParameter(SERVICE).describedAs(SERVICE_NAME).ofType(getStringType());
    parameterGroup.withRequiredParameter(WSDL_LOCATION).describedAs(URI_TO_FIND_THE_WSDL).ofType(getStringType())
        .withExpressionSupport(NOT_SUPPORTED)
        .withModelProperty(PARAMETER_MODEL_PROPERTY);

    OperationDeclarer operation =
        extensionDeclarer.withOperation(CONSUMER).describedAs(GO_GET_THEM_TIGER)
            .withModelProperty(OPERATION_MODEL_PROPERTY);
    operation.supportsStreaming(true).withOutput().ofType(getBinaryType());
    operation.withOutputAttributes().ofType(getStringType());

    parameterGroup = operation.onParameterGroup(OPERATION_PARAMETER_GROUP);
    parameterGroup.withRequiredParameter(OPERATION).describedAs(THE_OPERATION_TO_USE).ofType(getStringType());
    parameterGroup.withOptionalParameter(MTOM_ENABLED).describedAs(MTOM_DESCRIPTION).ofType(getBooleanType()).defaultingTo(true);

    operation = extensionDeclarer.withOperation(BROADCAST).describedAs(BROADCAST_DESCRIPTION);
    operation.withOutput().ofType(typeBuilder.voidType().build());
    operation.withOutputAttributes().ofType(getStringType());
    parameterGroup = operation.onParameterGroup(OPERATION_PARAMETER_GROUP);
    parameterGroup.withRequiredParameter(COLLECTION_PARAMETER).describedAs(THE_OPERATION_TO_USE).ofType(typeBuilder.arrayType()
        .of(typeBuilder.stringType()).build());

    parameterGroup.withOptionalParameter(MTOM_ENABLED).describedAs(MTOM_DESCRIPTION).ofType(getBooleanType())
        .defaultingTo(true);
    parameterGroup.withRequiredParameter(CALLBACK).describedAs(CALLBACK_DESCRIPTION).ofType(getObjectType(OperationModel.class))
        .withExpressionSupport(REQUIRED);

    operation = extensionDeclarer.withOperation(ARG_LESS).describedAs(HAS_NO_ARGS);
    operation.withOutput().ofType(getNumberType());
    operation.withOutputAttributes().ofType(getStringType());

    FunctionDeclarer function = extensionDeclarer.withFunction(FUNCTION_NAME).describedAs(FUNCTION_DESCRIPTION);
    function.onParameterGroup(DEFAULT_GROUP_NAME).withRequiredParameter(USERNAME).describedAs(USERNAME_DESCRIPTION)
        .ofType(getStringType());
    function.withOutput().ofType(getNumberType());

    ConnectionProviderDeclarer connectionProvider =
        extensionDeclarer.withConnectionProvider(CONNECTION_PROVIDER_NAME)
            .describedAs(CONNECTION_PROVIDER_DESCRIPTION)
            .withConnectionManagementType(NONE)
            .withExternalLibrary(EXTERNAL_LIBRARY_MODEL);

    parameterGroup = connectionProvider.onParameterGroup(CONNECTION_PROVIDER_PARAMETER_GROUP);
    parameterGroup.withRequiredParameter(USERNAME).describedAs(USERNAME_DESCRIPTION).ofType(getStringType());
    parameterGroup.withRequiredParameter(PASSWORD).describedAs(PASSWORD_DESCRIPTION).ofType(getStringType());

    SourceDeclarer sourceDeclarer =
        extensionDeclarer.withMessageSource(LISTENER).describedAs(LISTEN_DESCRIPTION).withModelProperty(SOURCE_MODEL_PROPERTY);

    sourceDeclarer.supportsStreaming(true).withOutput().ofType(getBinaryType());
    sourceDeclarer.withOutputAttributes().ofType(getObjectType(Serializable.class));
    sourceDeclarer.requiresConnection(true);

    parameterGroup = sourceDeclarer.onParameterGroup(SOURCE_PARAMETER_GROUP);
    parameterGroup.withRequiredParameter(URL).describedAs(URL_DESCRIPTION).ofType(getStringType());
    parameterGroup.withOptionalParameter(PORT).describedAs(PORT_DESCRIPTION).ofType(getNumberType())
        .defaultingTo(DEFAULT_PORT);

    return extensionDeclarer;
  }

  public ExtensionDeclarer getExtensionDeclarer() {
    return extensionDeclarer;
  }

  private static class TestModelProperty implements ModelProperty {

    private final String name;

    private TestModelProperty(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public boolean isPublic() {
      return true;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof TestModelProperty) {
        return name.equals(((TestModelProperty) obj).name);
      }

      return false;
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }
  }
}
