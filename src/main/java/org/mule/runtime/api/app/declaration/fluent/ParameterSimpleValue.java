/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ParameterElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterValue;
import org.mule.runtime.api.app.declaration.ParameterValueVisitor;

/**
 * Represents the configured simple value of a given {@link ParameterElementDeclaration}.
 *
 * @since 1.0
 */
public final class ParameterSimpleValue implements ParameterValue {

  private final String value;

  private ParameterSimpleValue(String value) {
    this.value = value;
  }

  public static ParameterValue of(String value) {
    return new ParameterSimpleValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(ParameterValueVisitor valueVisitor) {
    valueVisitor.visitSimpleValue(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ParameterSimpleValue that = (ParameterSimpleValue) o;

    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  public String getValue() {
    return value;
  }
}
