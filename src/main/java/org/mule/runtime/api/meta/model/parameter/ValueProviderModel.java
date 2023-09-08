/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.parameter;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

import org.mule.api.annotation.NoExtend;
import org.mule.api.annotation.NoInstantiate;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.value.Value;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Model for {@link ParameterModel} to communicate if one of these are capable to provide {@link Value values}.
 * <p>
 * The element with this model will considered as a one that provides values.
 *
 * @since 1.0
 */
@NoExtend
@NoInstantiate
public class ValueProviderModel implements EnrichableModel {

  private List<String> actingParameters;
  private final List<ActingParameterModel> parameters;
  private final Integer partOrder;
  private final String providerName;
  private final String providerId;
  private final boolean requiresConfiguration;
  private final boolean requiresConnection;
  private final boolean isOpen;
  private Map<Class<? extends ModelProperty>, ModelProperty> modelProperties = new LinkedHashMap<>();
  private transient Set<ModelProperty> modelPropertiesValues = new HashSet<>();

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
   *
   * @since 1.4.0
   */
  public ValueProviderModel(List<ActingParameterModel> parameters, boolean requiresConfiguration,
                            boolean requiresConnection,
                            boolean isOpen,
                            Integer partOrder, String providerName, String providerId) {
    checkArgument(parameters != null, "'parameters' can't be null");
    checkArgument(partOrder != null, "'valueParts' can't be null");
    checkArgument(providerName != null, "'providerName' can't be null");
    checkArgument(providerId != null, "'providerId' can't be null");
    this.isOpen = isOpen;
    this.parameters = unmodifiableList(parameters);
    this.requiresConfiguration = requiresConfiguration;
    this.requiresConnection = requiresConnection;
    this.partOrder = partOrder;
    this.providerName = providerName;
    this.providerId = providerId;
  }

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
   * @param modelProperties       the model properties this model is enriched with
   *
   * @since 1.4.0
   */
  public ValueProviderModel(List<ActingParameterModel> parameters, boolean requiresConfiguration,
                            boolean requiresConnection,
                            boolean isOpen,
                            Integer partOrder, String providerName, String providerId,
                            ModelProperty... modelProperties) {
    checkArgument(parameters != null, "'parameters' can't be null");
    checkArgument(partOrder != null, "'valueParts' can't be null");
    checkArgument(providerName != null, "'providerName' can't be null");
    checkArgument(providerId != null, "'providerId' can't be null");
    checkArgument(modelProperties != null, "'modelProperties' can't be null");
    this.isOpen = isOpen;
    this.parameters = unmodifiableList(parameters);
    this.requiresConfiguration = requiresConfiguration;
    this.requiresConnection = requiresConnection;
    this.partOrder = partOrder;
    this.providerName = providerName;
    this.providerId = providerId;
    this.modelProperties =
        stream(modelProperties).collect(toMap(modelProperty -> modelProperty.getClass(), modelProperty -> modelProperty));
    this.modelPropertiesValues = unmodifiableSet(new LinkedHashSet<>(this.modelProperties.values()));
  }

  /**
   * @return the list of parameters that are required to execute the Value Provider resolution.
   *
   * @deprecated since 1.4.0, use {@link #getParameters()} instead.
   */
  @Deprecated
  public List<String> getActingParameters() {
    if (actingParameters == null) {
      actingParameters = parameters.stream().filter(ActingParameterModel::isRequired)
          .map(ActingParameterModel::getName).collect(toList());
    }
    return actingParameters;
  }

  /**
   * @return the list of parameters that the Value Provider takes into account for its resolution
   *
   * @since 1.4.0
   */
  public List<ActingParameterModel> getParameters() {
    return parameters;
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

  /**
   * @return The id of the element that provides values
   * @since 1.4.0
   */
  public String getProviderId() {
    return providerId;
  }

  /**
   * @return a boolean indicating if the configuration is required to resolve the values
   */
  public boolean requiresConfiguration() {
    return requiresConfiguration;
  }

  /**
   * @return a boolean indicating if the connection is required to resolve the values
   */
  public boolean requiresConnection() {
    return requiresConnection;
  }

  /**
   * @return a boolean indicating if the calculated values should be considered as an open or closed set
   */
  public boolean isOpen() {
    return isOpen;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ValueProviderModel that = (ValueProviderModel) o;

    return new EqualsBuilder()
        .append(parameters, that.parameters)
        .append(partOrder, that.partOrder)
        .append(providerName, that.providerName)
        .append(providerId, that.providerId)
        .append(requiresConnection, that.requiresConnection)
        .append(requiresConfiguration, that.requiresConfiguration)
        .append(isOpen, that.isOpen)
        .append(modelProperties, that.modelProperties)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(parameters)
        .append(partOrder)
        .append(providerName)
        .append(providerId)
        .append(requiresConnection)
        .append(requiresConfiguration)
        .append(isOpen)
        .append(modelProperties)
        .toHashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T extends ModelProperty> Optional<T> getModelProperty(Class<T> propertyType) {
    checkArgument(propertyType != null, "Cannot get model properties of a null type");
    return ofNullable((T) modelProperties.get(propertyType));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<ModelProperty> getModelProperties() {
    if (modelPropertiesValues == null) {
      modelPropertiesValues = unmodifiableSet(new LinkedHashSet<>(modelProperties.values()));
    }
    return modelPropertiesValues;
  }
}
