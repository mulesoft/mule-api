/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

/**
 * Adds {@link this#getRefName} and {@link this#setRefName} capabilities to an {@link ElementDeclaration}
 * that represents a global element that can be referenced by its name.
 *
 * @see ComponentElementDeclaration#getConfigRef
 *
 * @since 1.0
 */
public interface ReferableElementDeclaration {

  /**
   * @return the configured name of the element that can be used to reference
   * it in the context of an {@link ArtifactDeclaration}
   */
  String getRefName();

  /**
   * Sets the configured name of the element that can be used to reference
   * it in the context of an {@link ArtifactDeclaration}
   * @param referableName  the configured name of the element
   */
  void setRefName(String referableName);
}
