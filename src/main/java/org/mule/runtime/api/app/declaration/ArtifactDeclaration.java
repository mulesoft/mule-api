/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a mule artifact configuration.
 *
 * @since 1.0
 */
public final class ArtifactDeclaration {

  private List<TopLevelParameterDeclaration> globalParameters = new LinkedList<>();
  private List<ConfigurationElementDeclaration> configs = new LinkedList<>();
  private List<FlowElementDeclaration> flows = new LinkedList<>();

  public ArtifactDeclaration() {}

  /**
   * @return the {@link List} of {@link FlowElementDeclaration flows} associated with
   * {@code this} {@link ArtifactDeclaration}
   */
  public List<FlowElementDeclaration> getFlows() {
    return unmodifiableList(flows);
  }

  /**
   * @param declaration
   * @return
   */
  public ArtifactDeclaration addFlow(FlowElementDeclaration declaration) {
    flows.add(declaration);
    return this;
  }

  /**
   * @return the {@link List} of {@link ConfigurationElementDeclaration configs} associated with
   * {@code this} {@link ArtifactDeclaration}
   */
  public List<ConfigurationElementDeclaration> getConfigs() {
    return unmodifiableList(configs);
  }

  /**
   *
   * @param declaration
   * @return
   */
  public ArtifactDeclaration addConfiguration(ConfigurationElementDeclaration declaration) {
    configs.add(declaration);
    return this;
  }

  /**
   * @return the {@link List} of {@link TopLevelParameterDeclaration global parameters} associated with
   * {@code this} {@link ArtifactDeclaration}
   */
  public List<TopLevelParameterDeclaration> getGlobalParameters() {
    return unmodifiableList(globalParameters);
  }

  /**
   *
   * @param declaration
   * @return
   */
  public ArtifactDeclaration addGlobalParameter(TopLevelParameterDeclaration declaration) {
    globalParameters.add(declaration);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ArtifactDeclaration that = (ArtifactDeclaration) o;

    if (!globalParameters.equals(that.globalParameters)) {
      return false;
    }
    if (!configs.equals(that.configs)) {
      return false;
    }
    return flows.equals(that.flows);
  }

  @Override
  public int hashCode() {
    int result = globalParameters.hashCode();
    result = 31 * result + configs.hashCode();
    result = 31 * result + flows.hashCode();
    return result;
  }
}
