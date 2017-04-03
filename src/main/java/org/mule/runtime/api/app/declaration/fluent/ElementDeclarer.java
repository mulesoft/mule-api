/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.ConnectionElementDeclaration;
import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.FlowElementDeclaration;
import org.mule.runtime.api.app.declaration.OperationElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;
import org.mule.runtime.api.app.declaration.RouterElementDeclaration;
import org.mule.runtime.api.app.declaration.ScopeElementDeclaration;
import org.mule.runtime.api.app.declaration.SourceElementDeclaration;
import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;

/**
 * Base declarer for a given {@link ElementDeclaration}
 *
 * @since 1.0
 */
public final class ElementDeclarer {

  private final String extension;

  private ElementDeclarer(String extension) {
    this.extension = extension;
  }

  public static ElementDeclarer forExtension(String name) {
    return new ElementDeclarer(name);
  }

  public ConfigurationElementDeclarer newConfiguration(String name) {
    return new ConfigurationElementDeclarer(new ConfigurationElementDeclaration(extension, name));
  }

  public ConnectionElementDeclarer newConnection(String name) {
    return new ConnectionElementDeclarer(new ConnectionElementDeclaration(extension, name));
  }

  public OperationElementDeclarer newOperation(String name) {
    return new OperationElementDeclarer(new OperationElementDeclaration(extension, name));
  }

  public SourceElementDeclarer newSource(String name) {
    return new SourceElementDeclarer(new SourceElementDeclaration(extension, name));
  }

  public ScopeElementDeclarer<ScopeElementDeclarer, ScopeElementDeclaration> newScope(String name) {
    return new ScopeElementDeclarer<>(new ScopeElementDeclaration(extension, name));
  }

  public RouterElementDeclarer newRouter(String name) {
    return new RouterElementDeclarer(new RouterElementDeclaration(extension, name));
  }

  public TopLevelParameterDeclarer newGlobalParameter(String name) {
    return new TopLevelParameterDeclarer(new TopLevelParameterDeclaration(extension, name));
  }

  public RouteElementDeclarer newRoute(String name) {
    return new RouteElementDeclarer(new RouteElementDeclaration(extension, name));
  }

  public static ArtifactDeclarer newArtifact() {
    return new ArtifactDeclarer(new ArtifactDeclaration());
  }

  public static FlowElementDeclarer newFlow(String name) {
    return new FlowElementDeclarer(new FlowElementDeclaration(name));
  }

  public static ParameterObjectValue.Builder newObjectValue() {
    return ParameterObjectValue.builder();
  }

  public static ParameterListValue.Builder newListValue() {
    return ParameterListValue.builder();
  }

}
