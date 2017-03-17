/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.TopLevelParameterDeclaration;

/**
 * Allows configuring an {@link TopLevelParameterDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class TopLevelParameterDeclarer
    extends EnrichableElementDeclarer<TopLevelParameterDeclarer, TopLevelParameterDeclaration> {

  TopLevelParameterDeclarer(TopLevelParameterDeclaration declaration) {
    super(declaration);
  }

  public TopLevelParameterDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

  public TopLevelParameterDeclarer withValue(ParameterObjectValue value) {
    declaration.setValue(value);
    return this;
  }
}
