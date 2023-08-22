/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.data.sample;

import static java.util.Collections.unmodifiableList;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.meta.model.HasOutputModel;
import org.mule.runtime.api.meta.model.parameter.ActingParameterModel;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Model for a {@link HasOutputModel} to communicate if it supports fetching sample data and what it requires.
 * <p>
 * The provider has the concept of a {@link #getProviderId()}, which unequivocally identifies each resolver within the module.
 * When the same provider implementation is used in several components, those components will all reference the same provider id.
 *
 * @since 1.4
 */
@NoInstantiate
public final class SampleDataProviderModel {

  private final List<ActingParameterModel> parameters;
  private final String providerId;
  private final boolean requiresConfiguration;
  private final boolean requiresConnection;

  /**
   * Creates a new instance
   *
   * @param parameters            the list of parameters that the Sample Data Provider takes into account for its resolution
   * @param providerId            the id which unequivocally identifies each provider
   * @param requiresConfiguration indicates if the configuration is required to fetch the sample
   * @param requiresConnection    indicates if the connection is required to fetch the sample
   */
  public SampleDataProviderModel(List<ActingParameterModel> parameters,
                                 String providerId,
                                 boolean requiresConfiguration,
                                 boolean requiresConnection) {
    checkArgument(parameters != null, "parameters cannot be null");
    checkArgument(providerId != null && providerId.length() > 0, "providerId cannot be blank");

    this.parameters = unmodifiableList(parameters);
    this.requiresConfiguration = requiresConfiguration;
    this.requiresConnection = requiresConnection;
    this.providerId = providerId;
  }

  /**
   * @return the list of parameters that the Sample Data Provider takes into account for its resolution
   */
  public List<ActingParameterModel> getParameters() {
    return parameters;
  }

  /**
   * @return the provider's id
   */
  public String getProviderId() {
    return providerId;
  }

  /**
   * @return a boolean indicating if the configuration is required to fetch the values
   */
  public boolean requiresConfiguration() {
    return requiresConfiguration;
  }

  /**
   * @return a boolean indicating if the connection is required to fetch the values
   */
  public boolean requiresConnection() {
    return requiresConnection;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SampleDataProviderModel that = (SampleDataProviderModel) o;

    return new EqualsBuilder()
        .append(parameters, that.parameters)
        .append(providerId, that.providerId)
        .append(requiresConnection, that.requiresConnection)
        .append(requiresConfiguration, that.requiresConfiguration)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(parameters)
        .append(providerId)
        .append(requiresConnection)
        .append(requiresConfiguration)
        .toHashCode();
  }
}
