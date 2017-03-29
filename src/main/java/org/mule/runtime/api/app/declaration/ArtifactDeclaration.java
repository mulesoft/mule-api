/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

/**
 * A programmatic descriptor of a mule artifact configuration.
 *
 * @since 1.0
 */
public final class ArtifactDeclaration extends EnrichableElementDeclaration {

  private List<GlobalElementDeclaration> globalElements = new LinkedList<>();

  public ArtifactDeclaration() {}

  /**
   * @return the {@link List} of {@link GlobalElementDeclaration global elements} associated with
   * {@code this} {@link ArtifactDeclaration}
   */
  public List<GlobalElementDeclaration> getGlobalElements() {
    return unmodifiableList(globalElements);
  }

  /**
   * Adds a property to the {@link ElementDeclaration}.
   * This property is meant to hold only metadata of the declaration.
   *
   * @param declaration the {@link GlobalElementDeclaration} to add.
   */
  public ArtifactDeclaration addGlobalElement(GlobalElementDeclaration declaration) {
    globalElements.add(declaration);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ArtifactDeclaration that = (ArtifactDeclaration) o;
    return globalElements.equals(that.globalElements);
  }

  @Override
  public int hashCode() {
    return globalElements.hashCode();
  }
}
