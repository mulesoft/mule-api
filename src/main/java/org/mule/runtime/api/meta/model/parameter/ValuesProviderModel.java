/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static org.mule.runtime.api.util.Preconditions.checkNotNull;
import org.mule.runtime.api.values.Value;

import java.util.List;
import java.util.Map;

/**
 * Model for {@link ParameterModel} and {@link ParameterGroupModel} to communicate if one of these are capable of
 * provide {@link Value values}.
 * <p>
 * The element with this model will considered as a one that provides values, and also this model communicates
 * how is the structure of th
 * @since 1.0
 */
public class ValuesProviderModel {

  private final List<String> requiredParameters;
  private final Map<Integer, String> valueParts;
  private String category;

  /**
   * Creates a new instance
   *
   * @param requiredParameters the list of parameters that are required to execute the Value Provider resolution
   * @param valueParts         the parts of the {@link Value}
   * @param category           the category of the associated value provider for this parameter or parameter group
   */
  public ValuesProviderModel(List<String> requiredParameters, Map<Integer, String> valueParts, String category) {
    checkNotNull(requiredParameters, "'requiredParameters' can't be null");
    checkNotNull(valueParts, "'valueParts' can't be null");
    checkNotNull(category, "'category' can't be null");
    this.requiredParameters = requiredParameters;
    this.valueParts = valueParts;
    this.category = category;
  }

  /**
   * @return the list of parameters that are required to execute the Value Provider resolution.
   */
  public List<String> getRequiredParameters() {
    return requiredParameters;
  }

  /**
   * @return the parts of the {@link Value}.
   */
  public Map<Integer, String> getValueParts() {
    return valueParts;
  }

  /**
   * @return the category of the associated value provider for this parameter or parameter group
   */
  public String getCategory() {
    return category;
  }
}
