/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.meta.model.declaration.fluent;

import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A declaration object for a {@link ParameterGroupModel}. It contains raw,
 * unvalidated data which is used to declare the structure of a {@link ParameterGroupModel}.
 *
 * @since 1.0
 */
public class ParameterGroupDeclaration extends NamedDeclaration<ParameterGroupDeclaration> {

  private final List<ParameterDeclaration> parameters = new LinkedList<>();
  private List<ExclusiveParametersDeclaration> exclusiveParameters = new LinkedList<>();
  private LayoutModel layoutModel;
  private boolean showInline = false;

  /**
   * {@inheritDoc}
   */
  public ParameterGroupDeclaration(String name) {
    super(name);
  }

  /**
   * @return an unmodifiable {@link List} with the {@link ParameterDeclaration declarations}
   * registered through {@link #addParameter(ParameterDeclaration)}
   */
  public List<ParameterDeclaration> getParameters() {
    return parameters;
  }

  /**
   * Adds a {@link ParameterDeclaration}
   *
   * @param parameter a not {@code null} {@link ParameterDeclaration}
   * @return this declaration
   * @throws {@link IllegalArgumentException} if {@code parameter} is {@code null}
   */
  public ParameterGroupDeclaration addParameter(ParameterDeclaration parameter) {
    checkArgument(parameter != null, "Can't add a null parameter");

    parameters.add(parameter);
    return this;
  }

  public ParameterGroupDeclaration addExclusiveParameters(Set<String> parameterNames, boolean requiresOne) {
    exclusiveParameters.add(new ExclusiveParametersDeclaration(parameterNames, requiresOne));
    return this;
  }

  public List<ExclusiveParametersDeclaration> getExclusiveParameters() {
    return exclusiveParameters;
  }

  public LayoutModel getLayoutModel() {
    return layoutModel;
  }

  public void setLayoutModel(LayoutModel layoutModel) {
    this.layoutModel = layoutModel;
  }

  public void showInline(boolean showInline) {
    this.showInline = showInline;
  }

  public boolean isShowInline() {
    return showInline;
  }
}
