/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration;

import org.mule.runtime.api.meta.model.ComponentModel;

/**
 * A programmatic descriptor of a {@link ComponentModel} configuration.
 *
 * @since 1.0
 */
public abstract class ComponentElementDeclaration extends ParameterizedElementDeclaration {

  private String configRef;

  public ComponentElementDeclaration() {}

  /**
   * Sets the {@code config-ref} parameter required for {@code this} component
   * @param configRef the {@link ReferableElementDeclaration#getRefName()}
   *                  of a {@link ConfigurationElementDeclaration}
   */
  public void setConfigRef(String configRef) {
    this.configRef = configRef;
  }

  /**
   * @return the {@code config-ref} parameter associated to {@code this} component
   */
  public String getConfigRef() {
    return configRef;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass() || !super.equals(o)) {
      return false;
    }

    ComponentElementDeclaration that = (ComponentElementDeclaration) o;
    return configRef != null ? configRef.equals(that.configRef) : that.configRef == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (configRef != null ? configRef.hashCode() : 0);
    return result;
  }
}
