/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;

/**
 * Base class for a declarer which allows adding {@link ParameterDeclaration}s
 *
 * @since 1.0
 */
public abstract class ParameterizedDeclarer<D extends ParameterizedDeclaration> extends Declarer<D> {

  /**
   * {@inheritDoc}
   */
  public ParameterizedDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Provides a {@link ParameterGroupDeclarer} for adding parameters
   * into a group of the given {@code name}. Use this if you wish to
   * declare a parameter which should belong to a specific group.
   *
   * @param name the name of the group to declare
   * @return a {@link ParameterGroupDeclarer}
   */
  public ParameterGroupDeclarer onParameterGroup(String name) {
    return new ParameterGroupDeclarer(declaration.getParameterGroup(name));
  }

  /**
   * Provides the default {@link ParameterGroupDeclarer}. Use this if you wish
   * to add a parameter which shouldn't belong to any specific group.
   *
   * @return a {@link ParameterGroupDeclarer}
   */
  public ParameterGroupDeclarer onDefaultParameterGroup() {
    return onParameterGroup(DEFAULT_GROUP_NAME);
  }
}
