/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import org.mule.runtime.api.meta.model.ModelProperty;
import org.mule.runtime.api.meta.model.display.DisplayModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;

import java.util.Set;

/**
 * Allows configuring a {@link ParameterGroupDeclaration} through a fluent API
 *
 * @since 1.0
 */
public class ParameterGroupDeclarer<D extends ParameterGroupDeclaration> extends Declarer<D>
    implements HasModelProperties<ParameterGroupDeclarer<D>>, HasDisplayModelDeclarer<ParameterGroupDeclarer<D>> {

  /**
   * Creates a new instance
   *
   * @param declaration a declaration
   */
  public ParameterGroupDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds a required parameter
   *
   * @param name the name of the parameter
   * @return a new {@link ParameterDeclarer}
   */
  public ParameterDeclarer withRequiredParameter(String name) {
    return new ParameterDeclarer(newParameter(name, true));
  }

  /**
   * Adds an optional parameter
   *
   * @param name the name of the parameter
   * @return a new {@link OptionalParameterDeclarer}
   */
  public OptionalParameterDeclarer withOptionalParameter(String name) {
    return new OptionalParameterDeclarer(newParameter(name, false));
  }

  public ParameterGroupDeclarer<D> withExclusiveOptionals(Set<String> parameterNames, boolean requiresOne) {
    declaration.addExclusiveParameters(parameterNames, requiresOne);
    return this;
  }

  /**
   * Sets the DSL representation type to be either inline, where the group is represented as a child element, or implicit, where
   * the group is shown as a set of attributes in the element.
   *
   * @param showInDsl {@code true} if the group is shown in the DSL
   * @return {@code this} {@link ParameterGroupDeclarer}
   */
  public ParameterGroupDeclarer<D> withDslInlineRepresentation(boolean showInDsl) {
    declaration.showInDsl(showInDsl);
    return this;
  }

  /**
   * Sets the given {@code layoutModel}
   *
   * @param layoutModel a {@link LayoutModel}
   * @return {@code this} declarer
   */
  public ParameterGroupDeclarer<D> withLayout(LayoutModel layoutModel) {
    declaration.setLayoutModel(layoutModel);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterGroupDeclarer<D> withModelProperty(ModelProperty modelProperty) {
    declaration.addModelProperty(modelProperty);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ParameterGroupDeclarer<D> withDisplayModel(DisplayModel displayModel) {
    declaration.setDisplayModel(displayModel);
    return this;
  }

  private ParameterDeclaration newParameter(String name, boolean required) {
    ParameterDeclaration parameter = new ParameterDeclaration(name);
    parameter.setRequired(required);
    declaration.addParameter(parameter);

    return parameter;
  }
}
