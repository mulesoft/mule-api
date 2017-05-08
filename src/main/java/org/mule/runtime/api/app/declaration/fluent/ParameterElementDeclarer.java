/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;

/**
 * Allows configuring an {@link ParameterElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ParameterElementDeclarer extends BaseElementDeclarer<ParameterElementDeclaration> {

  ParameterElementDeclarer(ParameterElementDeclaration declaration) {
    super(declaration);
  }

  /**
   * Associates the given {@code value} as part of {@code this} parameter configuration declaration
   *
   * @param text the {@code text} to associate with {@code this} parameter configuration
   * @return {@code this} declarer
   */
  public ParameterElementDeclarer withValue(String text) {
    return withValue(ParameterSimpleValue.of(text));
  }

  /**
   * Associates a {@link ParameterValue} as part of {@code this} parameter configuration declaration
   *
   * @param value the {@link ParameterValue} to associate with {@code this} parameter configuration
   * @return {@code this} declarer
   */
  public ParameterElementDeclarer withValue(ParameterValue value) {
    declaration.setValue(value);
    return this;
  }

}
