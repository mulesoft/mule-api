/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.ConfigurationElementDeclaration;
import org.mule.runtime.api.app.declaration.FlowElementDeclaration;
import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;

/**
 * Allows configuring an {@link ArtifactDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ArtifactDeclarer extends BaseElementDeclarer<ArtifactDeclaration> {

  ArtifactDeclarer(ArtifactDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link FlowElementDeclaration flow} to the {@link ArtifactDeclaration}
   *
   * @param flow the {@link FlowElementDeclaration flow} to add
   * @return {@code this} declarer
   */
  public ArtifactDeclarer withFlow(FlowElementDeclaration flow) {
    declaration.addFlow(flow);
    return this;
  }

  /**
   * Adds a {@link ConfigurationElementDeclaration config} to the {@link ArtifactDeclaration}
   *
   * @param config the {@link ConfigurationElementDeclaration config} to add
   * @return {@code this} declarer
   */
  public ArtifactDeclarer withConfig(ConfigurationElementDeclaration config) {
    declaration.addConfiguration(config);
    return this;
  }

  /**
   * Adds a {@link TopLevelParameterDeclaration parameter} to the {@link ArtifactDeclaration}
   *
   * @param parameter the {@link TopLevelParameterDeclaration parameter} to add
   * @return {@code this} declarer
   */
  public ArtifactDeclarer withGlobalParameter(TopLevelParameterDeclaration parameter) {
    declaration.addGlobalParameter(parameter);
    return this;
  }
}
