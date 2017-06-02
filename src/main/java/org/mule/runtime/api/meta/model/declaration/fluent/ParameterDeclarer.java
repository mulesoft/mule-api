/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.model.ParameterDslConfiguration;
import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.display.DisplayModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterRole;

/**
 * Allows configuring a {@link ParameterDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ParameterDeclarer<T extends ParameterDeclarer>
    implements HasModelProperties<ParameterDeclarer<T>>, HasType<ParameterDeclarer<T>>,
    HasDynamicType<ParameterDeclarer<T>>, HasDisplayModelDeclarer<ParameterDeclarer<T>> {

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
   * Marks the parameter as being a {@link ParameterModel#isOverrideFromConfig() config-override}.
   *
   * @return {@code this} declarer
   */
  public T asConfigOverride() {
    declaration.setConfigOverride(true);
    declaration.setRequired(false);
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
  public T withDisplay(DisplayModel displayModel) {
    declaration.setDisplayModel(displayModel);
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
}
