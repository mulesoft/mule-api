/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;
import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.ConnectionElementDeclaration;
import org.mule.runtime.api.app.declaration.ConstructElementDeclaration;
import org.mule.runtime.api.app.declaration.ElementDeclaration;
import org.mule.runtime.api.app.declaration.OperationElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterGroupElementDeclaration;
import org.mule.runtime.api.app.declaration.RouteElementDeclaration;
import org.mule.runtime.api.app.declaration.SourceElementDeclaration;
import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;
import org.mule.runtime.api.meta.model.ExtensionModel;

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

  /**
   * @return a new instance of an {@link ArtifactDeclarer}
   */
  public static ArtifactDeclarer newArtifact() {
    return new ArtifactDeclarer(new ArtifactDeclaration());
  }

  /**
   * @return a builder for a {@link ParameterObjectValue}
   */
  public static ParameterObjectValue.Builder newObjectValue() {
    return ParameterObjectValue.builder();
  }

  /**
   * @return a builder for a {@link ParameterListValue}
   */
  public static ParameterListValue.Builder newListValue() {
    return ParameterListValue.builder();
  }

  /**
   * @return a new instance of a {@link ParameterGroupElementDeclarer}
   */
  public static ParameterGroupElementDeclarer newParameterGroup() {
    return newParameterGroup(DEFAULT_GROUP_NAME);
  }

  /**
   * @return a new instance of a {@link ParameterGroupElementDeclarer} for a group with the given {@code name}
   */
  public static ParameterGroupElementDeclarer newParameterGroup(String name) {
    return new ParameterGroupElementDeclarer(new ParameterGroupElementDeclaration(name));
  }

  /**
   * Creates a new instance of a {@link ElementDeclarer declarer} associated to the {@link ExtensionModel Extension}
   * with the given {@code name}
   * @param name the {@link ExtensionModel#getName() name} of the {@link ExtensionModel Extension} to be associated
   *             to the {@link ElementDeclaration declarations} created with {@code this} {@link ElementDeclarer}
   * @return Creates a new instance of a {@link ElementDeclarer declarer} associated to the referenced
   * {@link ExtensionModel Extension}
   */
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

  public ConstructElementDeclarer newConstruct(String name) {
    return new ConstructElementDeclarer(new ConstructElementDeclaration(extension, name));
  }

  public TopLevelParameterDeclarer newGlobalParameter(String name) {
    return new TopLevelParameterDeclarer(new TopLevelParameterDeclaration(extension, name));
  }

  public RouteElementDeclarer newRoute(String name) {
    return new RouteElementDeclarer(new RouteElementDeclaration(extension, name));
  }

}
