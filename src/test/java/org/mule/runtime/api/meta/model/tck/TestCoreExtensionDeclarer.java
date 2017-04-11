/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.tck;

import static org.mule.runtime.api.meta.Category.COMMUNITY;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.Stereotype;
import org.mule.runtime.api.meta.model.XmlDslModel;
import org.mule.runtime.api.meta.model.declaration.fluent.ExtensionDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.RouterDeclarer;
import org.mule.runtime.api.meta.model.declaration.fluent.ScopeDeclarer;

/**
 * A simple pojo containing reference information for making test around a {@link ExtensionDeclarer}
 * which represents an oversimplified &quot;core&quot; extension with Mule's main language elements.
 * <p>
 * It contains an actual {@link ExtensionDeclarer} that can be accessed through the {@link #getExtensionDeclarer()}
 * method plus some other getters which provides access to other declaration components
 * that you might want to make tests against.
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
  public static final MuleVersion MIN_MULE_VERSION = new MuleVersion("4.0");
  public static final Stereotype FOREACH_STEREOTYPE = new Stereotype("foreacheable");

  private final ExtensionDeclarer extensionDeclarer = new ExtensionDeclarer();

  public TestCoreExtensionDeclarer() {
    declareOn(extensionDeclarer);
  }

  public ExtensionDeclarer declareOn(ExtensionDeclarer extensionDeclarer) {
    extensionDeclarer.named(EXTENSION_NAME)
        .describedAs(EXTENSION_DESCRIPTION)
        .fromVendor(VENDOR)
        .onVersion(VERSION)
        .withCategory(COMMUNITY)
        .withMinMuleVersion(MIN_MULE_VERSION)
        .withXmlDsl(XmlDslModel.builder().build());

    RouterDeclarer router = extensionDeclarer.withRouter(CHOICE_OPERATION_NAME);
    router.withOutput().ofType(getVoidType());
    router.withOutputAttributes().ofType(getAttributesType());

    router.withRoute(WHEN_ROUTE_NAME).withMinOccurs(1);
    router.withRoute(OTHERWISE_ROUTE_NAME).withMinOccurs(0).withMaxOccurs(1);

    ScopeDeclarer scope = extensionDeclarer.withScope(FOREACH_OPERATION_NAME);
    scope.withOutput().ofType(getVoidType());
    scope.withOutputAttributes().ofType(getAttributesType());
    scope.onDefaultParameterGroup().withOptionalParameter(FOREACH_EXPRESSION_PARAMETER_NAME).ofType(getStringType());

    return extensionDeclarer;
  }

  public ExtensionDeclarer getExtensionDeclarer() {
    return extensionDeclarer;
  }
}
