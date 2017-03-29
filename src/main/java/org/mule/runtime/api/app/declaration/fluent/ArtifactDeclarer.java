/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ArtifactDeclaration;
import org.mule.runtime.api.app.declaration.GlobalElementDeclaration;

/**
 * Allows configuring an {@link ArtifactDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ArtifactDeclarer extends EnrichableElementDeclarer<ArtifactDeclarer, ArtifactDeclaration> {

  ArtifactDeclarer(ArtifactDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link GlobalElementDeclaration global element} to the {@link ArtifactDeclaration}
   *
   * @param element the {@link GlobalElementDeclaration global element} to add
   * @return {@code this} declarer
   */
  public ArtifactDeclarer withGlobalElement(GlobalElementDeclaration element) {
    declaration.addGlobalElement(element);
    return this;
  }
}
