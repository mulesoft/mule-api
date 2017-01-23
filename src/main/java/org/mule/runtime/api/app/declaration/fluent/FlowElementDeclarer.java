/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ComponentElementDeclaration;
import org.mule.runtime.api.app.declaration.FlowElementDeclaration;

/**
 * Allows configuring an {@link FlowElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class FlowElementDeclarer extends BaseElementDeclarer<FlowElementDeclaration> {

  FlowElementDeclarer(FlowElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ComponentElementDeclaration component} to the {@link FlowElementDeclaration}
   *
   * @param component the {@link ComponentElementDeclaration component} to add
   * @return {@code this} declarer
   */
  public FlowElementDeclarer withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return this;
  }

  /**
   * Configures the initial state of the flow
   *
   * @param initialState initial state of {@code this} flow
   * @return {@code this} declarer
   */
  public FlowElementDeclarer withInitialState(String initialState) {
    declaration.setInitialState(initialState);
    return this;
  }

  /**
   * Configures the processingStrategy of the flow
   *
   * @param processingStrategy the processingStrategy of {@code this} flow
   * @return {@code this} declarer
   */
  public FlowElementDeclarer withProcessingStrategy(String processingStrategy) {
    declaration.setProcessingStrategy(processingStrategy);
    return this;
  }
}
