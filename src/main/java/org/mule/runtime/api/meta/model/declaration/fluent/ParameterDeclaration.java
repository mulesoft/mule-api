/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.meta.ExpressionSupport.SUPPORTED;
import static org.mule.runtime.api.meta.model.ElementDslModel.getDefaultInstance;
import static org.mule.runtime.api.meta.model.parameter.ParameterRole.BEHAVIOUR;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.model.ElementDslModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterRole;

/**
 * A declaration object for a {@link ParameterModel}. It contains raw,
 * unvalidated data which is used to declare the structure of a {@link ParameterModel}.
 * <p>
 * By default, {@link #getExpressionSupport()} ()} returns {@link ExpressionSupport#SUPPORTED}.
 *
 * @since 1.0
 */
public class ParameterDeclaration extends NamedDeclaration<ParameterDeclaration> implements TypedDeclaration {

  private boolean required;
  private ExpressionSupport expressionSupport = SUPPORTED;
  private MetadataType type;
  private boolean hasDynamicType;
  private Object defaultValue = null;
  private ElementDslModel dslModel = getDefaultInstance();
  private LayoutModel layoutModel;
  private ParameterRole parameterRole = BEHAVIOUR;

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

  public ElementDslModel getDslModel() {
    return dslModel;
  }

  public void setDslModel(ElementDslModel dslModel) {
    this.dslModel = dslModel;
  }

  public LayoutModel getLayoutModel() {
    return layoutModel;
  }

  public void setLayoutModel(LayoutModel layoutModel) {
    this.layoutModel = layoutModel;
  }

  public ParameterRole getRole() {
    return parameterRole;
  }

  public void setParameterRole(ParameterRole parameterRole) {
    checkArgument(parameterRole != null, "parameterPurpose cannot be null");
    this.parameterRole = parameterRole;
  }
}
