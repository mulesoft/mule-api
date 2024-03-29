/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
