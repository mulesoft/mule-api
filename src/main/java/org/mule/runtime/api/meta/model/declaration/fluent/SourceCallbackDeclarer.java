/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

/**
 * Allows configuring a {@link SourceCallbackDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class SourceCallbackDeclarer extends ParameterizedDeclarer<SourceCallbackDeclarer, SourceCallbackDeclaration> {

  /**
   * {@inheritDoc}
   */
  public SourceCallbackDeclarer(SourceCallbackDeclaration declaration) {
    super(declaration);
  }
}
