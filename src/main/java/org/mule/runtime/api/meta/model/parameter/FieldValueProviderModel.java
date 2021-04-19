/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static java.util.Objects.hash;

import org.mule.runtime.api.value.Value;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Model for {@link ParameterModel} to communicate if one of these are capable to provide {@link Value values} for its fields.
 *
 * @since 1.4
 */
public class FieldValueProviderModel extends ValueProviderModel {

  private final String targetPath;

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
   * @param targetPath            the path of the field whose values are provided within the parameter
   *
   * @since 1.4.0
   */
  public FieldValueProviderModel(List<ActingParameterModel> parameters, boolean requiresConfiguration, boolean requiresConnection,
                                 boolean isOpen, Integer partOrder, String providerName, String providerId, String targetPath) {
    super(parameters, requiresConfiguration, requiresConnection, isOpen, partOrder, providerName, providerId);
    this.targetPath = targetPath;
  }

  public String getTargetPath() {
    return targetPath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FieldValueProviderModel that = (FieldValueProviderModel) o;

    return new EqualsBuilder()
        .append(targetPath, that.targetPath)
        .isEquals() && super.equals(o);
  }

  @Override
  public int hashCode() {
    return hash(super.hashCode(), targetPath);
  }

}
