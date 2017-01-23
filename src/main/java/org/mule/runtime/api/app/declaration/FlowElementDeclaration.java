/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of an application Flow configuration.
 *
 * @since 1.0
 */
public final class FlowElementDeclaration implements NamedElementDeclaration {

  private List<ComponentElementDeclaration> components = new LinkedList<>();
  private String name;
  private String initialState;
  private String processingStrategy;

  public FlowElementDeclaration() {}

  public FlowElementDeclaration(String name) {
    setName(name);
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} associated with
   * {@code this} {@link FlowElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  public FlowElementDeclaration addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return this;
  }

  /**
   * @return the configured initial state of the flow
   */
  public String getInitialState() {
    return initialState;
  }

  /**
   * Configures the initial state of the flow
   *
   * @param initialState initial state of {@code this} flow
   */
  public void setInitialState(String initialState) {
    this.initialState = initialState;
  }

  /**
   * @return the processingStrategy configured for {@code this} flow
   */
  public String getProcessingStrategy() {
    return processingStrategy;
  }

  /**
   * Configures the processingStrategy of the flow
   *
   * @param processingStrategy the processingStrategy of {@code this} flow
   */
  public void setProcessingStrategy(String processingStrategy) {
    this.processingStrategy = processingStrategy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(String name) {
    checkArgument(name != null && !name.trim().isEmpty(), "Flow declaration requires a non blank name");
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FlowElementDeclaration)) {
      return false;
    }

    FlowElementDeclaration that = (FlowElementDeclaration) o;
    return !(!name.equals(that.name) ||
        (initialState != null ? !initialState.equals(that.initialState) : that.initialState != null) ||
        !components.equals(that.components)) &&
        (processingStrategy != null ? processingStrategy.equals(that.processingStrategy) : that.processingStrategy == null);
  }

  @Override
  public int hashCode() {
    int result = components.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + (initialState != null ? initialState.hashCode() : 0);
    result = 31 * result + (processingStrategy != null ? processingStrategy.hashCode() : 0);
    return result;
  }
}
