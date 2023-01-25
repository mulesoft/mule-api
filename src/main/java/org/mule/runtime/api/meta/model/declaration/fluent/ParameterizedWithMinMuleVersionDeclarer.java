/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.MuleVersion;

/**
 * Base class for a component which can be used to create a {@link BaseDeclaration} and has a minimum version required for its
 * usage.
 *
 * @param <T> the generic type of {@link BaseDeclaration} to be created
 * @since 1.6
 */
public class ParameterizedWithMinMuleVersionDeclarer<T extends ParameterizedWithMinMuleVersionDeclarer, D extends ParameterizedWithMinMuleVersionDeclaration>
    extends ParameterizedDeclarer<T, D> implements HasMinMuleVersionDeclarer<T> {

  /**
   * Creates a new instance
   *
   * @param declaration a declaration which state is to be populated through {@code this} instance
   */
  public ParameterizedWithMinMuleVersionDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * {@inheritDoc}
   */
  public T withMinMuleVersion(MuleVersion minMuleVersion) {
    this.declaration.withMinMuleVersion(minMuleVersion);
    return (T) this;
  }
}
