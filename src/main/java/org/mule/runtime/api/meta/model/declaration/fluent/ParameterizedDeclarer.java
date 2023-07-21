/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.model.parameter.ParameterGroupModel.DEFAULT_GROUP_NAME;

/**
 * Base class for a declarer which allows adding {@link ParameterDeclaration}s
 *
 * @since 1.0
 */
public abstract class ParameterizedDeclarer<T extends ParameterizedDeclarer, D extends ParameterizedDeclaration>
    extends Declarer<D>
    implements HasParametersDeclarer {

  /**
   * {@inheritDoc}
   */
  public ParameterizedDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Provides a {@link ParameterGroupDeclarer} for adding parameters into a group of the given {@code name}. Use this if you wish
   * to declare a parameter which should belong to a specific group.
   *
   * @param name the name of the group to declare
   * @return a {@link ParameterGroupDeclarer}
   */
  public ParameterGroupDeclarer onParameterGroup(String name) {
    return new ParameterGroupDeclarer(declaration.getParameterGroup(name));
  }

  /**
   * Provides the default {@link ParameterGroupDeclarer}. Use this if you wish to add a parameter which shouldn't belong to any
   * specific group.
   *
   * @return a {@link ParameterGroupDeclarer}
   */
  public ParameterGroupDeclarer onDefaultParameterGroup() {
    return onParameterGroup(DEFAULT_GROUP_NAME);
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} declarer
   */
  public T describedAs(String description) {
    declaration.setDescription(description);
    return (T) this;
  }
}
