/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.test.meta.model.tck;

import static org.mule.metadata.api.model.MetadataFormat.JAVA;
import static org.mule.runtime.api.meta.Category.COMMUNITY;
import static org.mule.runtime.api.meta.model.nested.ChainExecutionOccurrence.ONCE_OR_NONE;

import org.mule.metadata.api.builder.BaseTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.metadata.api.model.VoidType;
import org.mule.metadata.api.model.impl.DefaultVoidType;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.declaration.fluent.ConstructDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.NestedRouteDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.OperationDeclarer;

/**
 * A simple pojo containing reference information for making test around a {@link ExtensionDeclarer} which represents an
 * oversimplified &quot;core&quot; extension with Mule's main language elements.
 * <p>
 * It contains an actual {@link ExtensionDeclarer} that can be accessed through the {@link #getExtensionDeclarer()} method plus
 * some other getters which provides access to other declaration components that you might want to make tests against.
 * <p>
 * This case focuses on the scenario of scopes and routers (although not necessarily limited to that)
 *
 * @since 1.0
 */
public class TestCoreExtensionDeclarer extends TestBaseDeclarer {

  public static final String EXTENSION_NAME = "core";
  public static final String EXTENSION_DESCRIPTION = "Core Extension";
  public static final String VENDOR = "Mulesoft";
  public static final String CHOICE_OPERATION_NAME = "choice";
  public static final String WHEN_ROUTE_NAME = "when";
  public static final String OTHERWISE_ROUTE_NAME = "otherwise";
  public static final String FOREACH_ROUTE_NAME = "default";
  public static final String FOREACH_OPERATION_NAME = "foreach";
  public static final String FOREACH_EXPRESSION_PARAMETER_NAME = "expression";
  public static final String VERSION = "1.0";

  private final ExtensionDeclarer extensionDeclarer = new ExtensionDeclarer();

  public TestCoreExtensionDeclarer() {
    declareOn(extensionDeclarer);
  }

  public ExtensionDeclarer compatibilityDeclareOn(ExtensionDeclarer extensionDeclarer) {
    extensionDeclarer.named(EXTENSION_NAME)
        .describedAs(EXTENSION_DESCRIPTION)
        .fromVendor(VENDOR)
        .onVersion(VERSION)
        .withCategory(COMMUNITY)
        .withXmlDsl(XmlDslModel.builder().build());

    ConstructDeclarer router = extensionDeclarer.withConstruct(CHOICE_OPERATION_NAME);

    NestedRouteDeclarer whenDeclaration = router.withRoute(WHEN_ROUTE_NAME);
    whenDeclaration.withMinOccurs(1).withChain().setExecutionOccurrence(ONCE_OR_NONE);
    whenDeclaration.withComponent("errorHandler")
        .onDefaultParameterGroup().withRequiredParameter("type").ofType(getStringType());
    router.withRoute(OTHERWISE_ROUTE_NAME).withMinOccurs(0).withMaxOccurs(1).withChain().setExecutionOccurrence(ONCE_OR_NONE);
    router.withSemanticTerm("router");
    ConstructDeclarer scope = extensionDeclarer.withConstruct(FOREACH_OPERATION_NAME);
    scope.onDefaultParameterGroup().withOptionalParameter(FOREACH_EXPRESSION_PARAMETER_NAME).ofType(getStringType());

    return extensionDeclarer;
  }

  public ExtensionDeclarer declareOn(ExtensionDeclarer extensionDeclarer) {
    extensionDeclarer.named(EXTENSION_NAME)
        .describedAs(EXTENSION_DESCRIPTION)
        .fromVendor(VENDOR)
        .onVersion(VERSION)
        .withCategory(COMMUNITY)
        .withXmlDsl(XmlDslModel.builder().setPrefix("test").build());

    MetadataType anytype = BaseTypeBuilder.create(JAVA).anyType().build();
    MetadataType voidTYpe = BaseTypeBuilder.create(JAVA).voidType().build();

    OperationDeclarer router = extensionDeclarer.withOperation(CHOICE_OPERATION_NAME);
    router.withOutput().ofType(anytype);
    router.withOutputAttributes().ofType(anytype);

    NestedRouteDeclarer whenDeclaration = router.withRoute(WHEN_ROUTE_NAME);
    whenDeclaration.withMinOccurs(1).withChain().setExecutionOccurrence(ONCE_OR_NONE);
    whenDeclaration.withComponent("errorHandler")
        .onDefaultParameterGroup().withRequiredParameter("type").ofType(getStringType());
    router.withRoute(OTHERWISE_ROUTE_NAME).withMinOccurs(0).withMaxOccurs(1).withChain().setExecutionOccurrence(ONCE_OR_NONE);
    router.withSemanticTerm("router");
    OperationDeclarer scope = extensionDeclarer.withOperation(FOREACH_OPERATION_NAME);
    scope.onDefaultParameterGroup().withOptionalParameter(FOREACH_EXPRESSION_PARAMETER_NAME).ofType(getStringType());
    scope.withOutput().ofType(voidTYpe);
    scope.withOutputAttributes().ofType(voidTYpe);

    return extensionDeclarer;
  }

  public ExtensionDeclarer getExtensionDeclarer() {
    return extensionDeclarer;
  }
}
