/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.source.SourceCallbackModel;

/**
 * A declaration object for a {@link SourceCallbackModel}. It contains raw, unvalidated
 * data which is used to declare the structure of a {@link SourceCallbackModel}
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
