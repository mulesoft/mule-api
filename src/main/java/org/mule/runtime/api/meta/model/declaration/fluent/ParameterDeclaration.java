/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.mule.runtime.api.meta.ExpressionSupport.SUPPORTED;
import static org.mule.runtime.api.meta.model.ParameterDslConfiguration.getDefaultInstance;
import static org.mule.runtime.api.meta.model.parameter.ParameterRole.BEHAVIOUR;
import static org.mule.runtime.api.util.Preconditions.checkArgument;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.MuleVersion;
import org.mule.runtime.api.meta.model.ParameterDslConfiguration;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.parameter.FieldValueProviderModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterRole;
import org.mule.runtime.api.meta.model.parameter.ValueProviderModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A declaration object for a {@link ParameterModel}. It contains raw, unvalidated data which is used to declare the structure of
 * a {@link ParameterModel}.
 * <p>
 * By default, {@link #getExpressionSupport()} ()} returns {@link ExpressionSupport#SUPPORTED}.
 *
 * @since 1.0
 */
public class ParameterDeclaration extends AbstractParameterDeclaration<ParameterDeclaration>
    implements TypedDeclaration, WithDeprecatedDeclaration, WithSemanticTermsDeclaration, WithMinMuleVersionDeclaration {

  private boolean required;
  private boolean isConfigOverride;
  private boolean isComponentId;
  private ExpressionSupport expressionSupport = SUPPORTED;
  private MetadataType type;
  private boolean hasDynamicType;
  private Object defaultValue = null;
  private ParameterDslConfiguration dslConfiguration = getDefaultInstance();
  private ParameterRole parameterRole = BEHAVIOUR;
  private ValueProviderModel valueProviderModel;
  private List<FieldValueProviderModel> fieldValueProviderModels = new ArrayList<>();
  private final List<StereotypeModel> allowedStereotypeModels = new ArrayList<>();
  private DeprecationModel deprecation;
  private MuleVersion minMuleVersion;
  private final Set<String> semanticTerms = new LinkedHashSet<>();

  /**
   * {@inheritDoc}
   */
  public ParameterDeclaration(String name) {
    super(name);
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public ExpressionSupport getExpressionSupport() {
    return expressionSupport;
  }

  public void setExpressionSupport(ExpressionSupport expressionSupport) {
    this.expressionSupport = expressionSupport;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MetadataType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setType(MetadataType type, boolean isDynamic) {
    this.type = type;
    this.hasDynamicType = isDynamic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasDynamicType() {
    return hasDynamicType;
  }

  public ParameterDslConfiguration getDslConfiguration() {
    return dslConfiguration;
  }

  public void setDslConfiguration(ParameterDslConfiguration dslConfiguration) {
    this.dslConfiguration = dslConfiguration;
  }

  public ParameterRole getRole() {
    return parameterRole;
  }

  public void setParameterRole(ParameterRole parameterRole) {
    checkArgument(parameterRole != null, "parameterPurpose cannot be null");
    this.parameterRole = parameterRole;
  }

  public boolean isConfigOverride() {
    return isConfigOverride;
  }

  public void setConfigOverride(boolean isConfigOverride) {
    this.isConfigOverride = isConfigOverride;
  }

  public ValueProviderModel getValueProviderModel() {
    return valueProviderModel;
  }

  public void setValueProviderModel(ValueProviderModel valueProviderModel) {
    this.valueProviderModel = valueProviderModel;
  }

  public List<FieldValueProviderModel> getFieldValueProviderModels() {
    return fieldValueProviderModels;
  }

  public void setFieldValueProviderModels(List<FieldValueProviderModel> fieldValueProviderModels) {
    this.fieldValueProviderModels = fieldValueProviderModels;
  }

  public void setAllowedStereotypeModels(List<StereotypeModel> allowedStereotypeModels) {
    this.allowedStereotypeModels.addAll(allowedStereotypeModels);
  }

  public List<StereotypeModel> getAllowedStereotypeModels() {
    return allowedStereotypeModels;
  }

  public boolean isComponentId() {
    return isComponentId;
  }

  public void setComponentId(boolean componentId) {
    isComponentId = componentId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<DeprecationModel> getDeprecation() {
    return ofNullable(deprecation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withDeprecation(DeprecationModel deprecation) {
    this.deprecation = deprecation;
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public Set<String> getSemanticTerms() {
    return semanticTerms;
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.4.0
   */
  @Override
  public void addSemanticTerm(String semanticTerm) {
    checkArgument(!isBlank(semanticTerm), "semantic terms cannot be blank");
    semanticTerms.add(semanticTerm);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<MuleVersion> getMinMuleVersion() {
    return ofNullable(minMuleVersion);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void withMinMuleVersion(MuleVersion minMuleVersion) {
    this.minMuleVersion = minMuleVersion;
  }
}
