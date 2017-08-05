/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import static java.util.Optional.empty;
import static org.mule.runtime.api.component.location.Location.SOURCE;

import java.util.List;
import java.util.Optional;

/**
 * A programmatic descriptor of an application Flow configuration.
 *
 * @since 1.0
 */
public final class ConstructElementDeclaration extends ComponentElementDeclaration<ConstructElementDeclaration>
    implements GlobalElementDeclaration {

  private String elementName;

  public ConstructElementDeclaration() {}

  public ConstructElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    if (getComponents().isEmpty()) {
      return empty();
    }

    if (parts.get(0).equals(SOURCE)) {
      ComponentElementDeclaration first = getComponents().get(0);
      return first instanceof SourceElementDeclaration ? Optional.of((T) first) : empty();
    }

    return super.findElement(parts);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(GlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRefName() {
    return elementName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRefName(String referableName) {
    this.elementName = referableName;
  }

}
