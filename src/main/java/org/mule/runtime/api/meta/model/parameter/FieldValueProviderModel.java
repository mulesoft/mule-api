/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static java.util.Objects.hash;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.value.Value;

import java.util.List;
import java.util.Objects;

/**
 * Model for {@link ParameterModel} to communicate if one of these are capable to provide {@link Value values} for its fields.
 *
 * @since 1.4
 */
@NoExtend
@NoInstantiate
public class FieldValueProviderModel extends ValueProviderModel {

  private final String targetSelector;

  /**
   * Creates a new instance
   *
   * @param parameters            the list of parameters that the Value Provider takes into account for its resolution
   * @param requiresConfiguration indicates if the configuration is required to resolve the values
   * @param requiresConnection    indicates if the connection is required to resolve the values
   * @param isOpen                indicates if the calculated values should be considered as an open or closed set
   * @param partOrder             the position in the value
   * @param providerName          the category of the associated value provider for this parameter
   * @param providerId            the id of the associated value provider for this parameter
   * @param targetSelector        the path of the field whose values are provided within the parameter
   *
   * @since 1.4.0
   */
  public FieldValueProviderModel(List<ActingParameterModel> parameters, boolean requiresConfiguration, boolean requiresConnection,
                                 boolean isOpen, Integer partOrder, String providerName, String providerId,
                                 String targetSelector) {
    super(parameters, requiresConfiguration, requiresConnection, isOpen, partOrder, providerName, providerId);
    this.targetSelector = targetSelector;
  }

  public String getTargetSelector() {
    return targetSelector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!super.equals(o)) {
      return false;
    }

    if (getClass() != o.getClass()) {
      return false;
    }

    FieldValueProviderModel that = (FieldValueProviderModel) o;
    return Objects.equals(targetSelector, that.targetSelector);
  }

  @Override
  public int hashCode() {
    return hash(super.hashCode(), targetSelector);
  }

}
