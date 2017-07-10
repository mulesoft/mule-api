/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static org.mule.runtime.api.util.Preconditions.checkNotNull;
import org.mule.runtime.api.value.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Model for {@link ParameterModel} to communicate if one of these are capable to provide {@link Value values}.
 * <p>
 * The element with this model will considered as a one that provides values.
 * @since 1.0
 */
public class ValueProviderModel {

  private final List<String> actingParameters;
  private final Integer partOrder;
  private final String providerName;

  /**
   * Creates a new instance
   *
   * @param actingParameters the list of parameters that are required to execute the Value Provider resolution
   * @param partOrder        the position in the value
   * @param providerName     the category of the associated value provider for this parameter
   */
  public ValueProviderModel(List<String> actingParameters, Integer partOrder, String providerName) {
    checkNotNull(actingParameters, "'actingParameters' can't be null");
    checkNotNull(partOrder, "'valueParts' can't be null");
    checkNotNull(providerName, "'providerName' can't be null");
    this.actingParameters = actingParameters;
    this.partOrder = partOrder;
    this.providerName = providerName;
  }

  /**
   * @return the list of parameters that are required to execute the Value Provider resolution.
   */
  public List<String> getActingParameters() {
    return actingParameters;
  }

  /**
   * @return the order of this part in the value
   */
  public Integer getPartOrder() {
    return partOrder;
  }

  /**
   * @return The name of the element that provides values
   */
  public String getProviderName() {
    return providerName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ValueProviderModel that = (ValueProviderModel) o;

    return new EqualsBuilder()
        .append(actingParameters, that.actingParameters)
        .append(partOrder, that.partOrder)
        .append(providerName, that.providerName)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(actingParameters)
        .append(partOrder)
        .append(providerName)
        .toHashCode();
  }
}
