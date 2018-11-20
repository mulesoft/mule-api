/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.ParameterDslConfiguration;
import org.mule.runtime.api.meta.model.deprecated.DeprecationModel;
import org.mule.runtime.api.meta.model.display.DisplayModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterRole;
import org.mule.runtime.api.meta.model.parameter.ValueProviderModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;

import java.util.List;

/**
 * Allows configuring a {@link ParameterDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ParameterDeclarer<T extends ParameterDeclarer>
    implements HasModelProperties<ParameterDeclarer<T>>, HasType<ParameterDeclarer<T>>,
    HasDynamicType<ParameterDeclarer<T>>, HasDisplayModelDeclarer<ParameterDeclarer<T>>,
    HasDeprecatedDeclarer<ParameterDeclarer<T>> {

  private final ParameterDeclaration declaration;

  ParameterDeclarer(ParameterDeclaration declaration) {
    this.declaration = declaration;
  }

  /**
   * Specifies the type of the {@link ParameterModel}
   *
   * @param type the type of the parameter
   * @return
   */
  @Override
  public T ofType(MetadataType type) {
    declaration.setType(type, false);
    return (T) this;
  }

  /**
   * Specifies the type of the {@link ParameterModel}
   *
   * @param type the type of the parameter
   * @return
   */
  @Override
  public T ofDynamicType(MetadataType type) {
    declaration.setType(type, true);
    return (T) this;
  }

  /**
   * Adds a description
   *
   * @param description a description
   * @return {@code this} descriptor
   */
  public T describedAs(String description) {
    declaration.setDescription(description);
    return (T) this;
  }

  public T withExpressionSupport(ExpressionSupport support) {
    declaration.setExpressionSupport(support);
    return (T) this;
  }

  /**
   * Describes the language which allows configuring this parameter
   *
   * @param dslModel an {@link ParameterDslConfiguration}
   * @return {@code this} declarer
   */
  public T withDsl(ParameterDslConfiguration dslModel) {
    declaration.setDslConfiguration(dslModel);
    return (T) this;
  }

  /**
   * Sets the given {@code layoutModel}
   *
   * @param layoutModel a {@link LayoutModel}
   * @return {@code this} declarer
   */
  public T withLayout(LayoutModel layoutModel) {
    declaration.setLayoutModel(layoutModel);
    return (T) this;
  }

  /**
   * Sets the purpose of the declared parameter
   *
   * @param role a {@link ParameterRole}
   * @return {@code this} declarer
   */
  public T withRole(ParameterRole role) {
    declaration.setParameterRole(role);
    return (T) this;
  }

  /**
   * Marks the parameter as being a {@link ParameterModel#isOverrideFromConfig() config-override}. This also means this is an
   * optional parameter.
   *
   * @return {@code this} declarer
   */
  public T asConfigOverride() {
    declaration.setConfigOverride(true);
    declaration.setRequired(false);
    return (T) this;
  }

  /**
   * Marks the parameter as being a {@link ParameterModel#isComponentId() component ID}. This also means this is a
   * {@code required} parameter where expressions are {@link ExpressionSupport::NOT_SUPPORTED}.
   *
   * @return {@code this} declarer
   */
  public T asComponentId() {
    declaration.setComponentId(true);
    declaration.setExpressionSupport(NOT_SUPPORTED);
    return (T) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterDeclarer<T> withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T withDisplayModel(DisplayModel displayModel) {
    declaration.setDisplayModel(displayModel);
    return (T) this;
  }

  /**
   * Sets the given {@code valueProviderModel}
   *
   * @param valueProviderModel a {@link ValueProviderModel}
   * @return {@code this} declarer
   */
  public T withValueProviderModel(ValueProviderModel valueProviderModel) {
    declaration.setValueProviderModel(valueProviderModel);
    return (T) this;
  }

  /**
   * Sets the given {@link List} of {@link StereotypeModel}
   * 
   * @param stereotypeModels a {@link List} of {@link StereotypeModel}
   * @return {@code this} declarer
   */
  public T withAllowedStereotypes(List<StereotypeModel> stereotypeModels) {
    declaration.setAllowedStereotypeModels(stereotypeModels);
    return (T) this;
  }

  /**
   * Gets the declaration object for this descriptor
   *
   * @return a {@link ParameterDeclaration}
   */
  public ParameterDeclaration getDeclaration() {
    return declaration;
  }

  @Override
  public ParameterDeclarer<T> withDeprecation(DeprecationModel deprecation) {
    declaration.withDeprecation(deprecation);
    return this;
  }
}
