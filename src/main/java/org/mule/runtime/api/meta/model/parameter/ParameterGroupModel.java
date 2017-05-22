/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.display.HasLayoutModel;

import java.util.List;
import java.util.Optional;

/**
 * A group is a logical way to display one or more parameters together. If no group is specified then
 * {@link #DEFAULT_GROUP_NAME} is used by default.
 *
 * @since 1.0
 */
public interface ParameterGroupModel extends NamedObject, DescribedObject, HasDisplayModel, HasLayoutModel, EnrichableModel {

  /**
   * Group name for parameters that are considered for general purposes and shouldn't belong to a particular
   * group.
   */
  String DEFAULT_GROUP_NAME = "General";

  /**
   * Group name for parameters that are considered for advanced usage.
   */
  String ADVANCED = "Advanced";

  /**
   * Group name for parameters that are for controling the opertion's output
   */
  String OUTPUT = "Output";

  /**
   * Group name for parameters that are considered to be part of a connection configuration.
   */
  String CONNECTION = "Connection";

  /**
   * Returns the {@link ParameterModel parameterModels}
   * available for {@code this} group
   *
   * @return a immutable {@link java.util.List} with {@link ParameterModel}
   * instances. It will never be empty
   */
  List<ParameterModel> getParameterModels();

  /**
   * Returns a list of {@link ExclusiveParametersModel} which describe
   * mutual exclusions between the {@link #getParameterModels() parameters}
   * @return a {@link List} of {@link ExclusiveParametersModel}
   */
  List<ExclusiveParametersModel> getExclusiveParametersModels();

  /**
   * Returns the {@link ParameterModel parameterModel} with the given {@code name}
   * if one is present.
   *
   * @return the {@link ParameterModel parameterModel} with the given {@code name}
   * or {@link Optional#empty()} if none is found.
   */
  Optional<ParameterModel> getParameter(String name);

  /**
   * @return {@code true} if this group should be represented inline
   * as a child element in the DSL
   */
  boolean isShowInDsl();
}
