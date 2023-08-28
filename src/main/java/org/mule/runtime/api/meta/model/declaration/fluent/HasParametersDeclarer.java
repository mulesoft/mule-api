/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.api.annotation.NoImplement;

/**
 * A contract interface for a declarer capable of adding a model properties
 *
 * @param <T> the type of the implementing type. Used to allow method chaining
 * @since 1.0
 */
@NoImplement
public interface HasParametersDeclarer {

  /**
   * Provides a {@link ParameterGroupDeclarer} for adding parameters into a group of the given {@code name}. Use this if you wish
   * to declare a parameter which should belong to a specific group.
   *
   * @param name the name of the group to declare
   * @return a {@link ParameterGroupDeclarer}
   */
  ParameterGroupDeclarer onParameterGroup(String name);

  /**
   * Provides the default {@link ParameterGroupDeclarer}. Use this if you wish to add a parameter which shouldn't belong to any
   * specific group.
   *
   * @return a {@link ParameterGroupDeclarer}
   */
  ParameterGroupDeclarer onDefaultParameterGroup();

}
