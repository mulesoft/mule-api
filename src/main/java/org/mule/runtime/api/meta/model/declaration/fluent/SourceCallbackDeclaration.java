/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.source.SourceCallbackModel;

/**
 * A declaration object for a {@link SourceCallbackModel}. It contains raw, unvalidated data which is used to declare the
 * structure of a {@link SourceCallbackModel}
 *
 * @since 1.0
 */
public class SourceCallbackDeclaration extends ParameterizedDeclaration<SourceCallbackDeclaration> {

  /**
   * {@inheritDoc}
   */
  public SourceCallbackDeclaration(String name) {
    super(name);
  }
}
