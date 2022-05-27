/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.component.declaration;

import static java.util.Collections.unmodifiableMap;

import org.mule.runtime.api.component.declaration.ComponentDeclaration;
import org.mule.runtime.api.component.declaration.ComponentDeclaration.Builder;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.util.Pair;

import java.util.HashMap;
import java.util.Map;


public class ComponentDeclarationBuilder<M extends ParameterizedModel> implements Builder<M> {

  private M model;

  private final Map<Pair<String, String>, Object> parameters = new HashMap<>();

  public Builder<M> withModel(M model) {
    this.model = model;

    return this;
  }

  @Override
  public Builder<M> withParameter(String paramGroupName, String paramName, Object paramValue) {
    // TODO W-11214382 validate the provided group/param exist in the model

    parameters.put(new Pair<>(paramGroupName, paramName), paramValue);

    return this;
  }

  @Override
  public ComponentDeclaration<M> build() {
    // TODO W-11214382 validate all required params are present
    return new DefaultComponentDeclaration<>(model, unmodifiableMap(parameters));
  }

  private static class DefaultComponentDeclaration<M extends ParameterizedModel> implements ComponentDeclaration<M> {

    private final M model;

    private final Map<Pair<String, String>, Object> parameters;

    public DefaultComponentDeclaration(M model, Map<Pair<String, String>, Object> parameters) {
      this.model = model;
      this.parameters = parameters;
    }

    @Override
    public M getModel() {
      return model;
    }


    @Override
    public Object getParameter(String paramGroupName, String paramName) {
      return parameters;
    }

    @Override
    public void forEachParameter(ParameterAction action) {
      parameters.entrySet().forEach(e -> action.accept(e.getKey().getFirst(), e.getKey().getSecond(), e.getValue()));
    }
  }
}
