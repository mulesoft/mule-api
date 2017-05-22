/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.app.declaration.fluent;

import org.mule.runtime.api.app.declaration.ParameterGroupElementDeclaration;
import org.mule.runtime.api.app.declaration.ParameterizedElementDeclaration;

/**
 * Allows configuring a {@link ParameterizedElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ParameterizedElementDeclarer<D extends ParameterizedElementDeclarer, T extends ParameterizedElementDeclaration>
    extends EnrichableElementDeclarer<D, T> {

  ParameterizedElementDeclarer(T declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ParameterGroupElementDeclaration parameter group} to {@code this} {@link ParameterizedElementDeclaration}
   *
   * @param group  the {@link ParameterGroupElementDeclarer group} to add in {@code this} {@link ParameterizedElementDeclaration}
   * @return {@code this} declarer
   */
  public D withParameterGroup(ParameterGroupElementDeclaration group) {
    declaration.addParameterGroup(group);
    return (D) this;
  }

}
