/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.internal.parameterization;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.parameterization.ComponentParameterization;
import org.mule.runtime.api.parameterization.ComponentParameterization.Builder;
import org.mule.runtime.api.util.Pair;

/**
 * Builder class for creating {@link ComponentParameterization} instances.
 *
 * The created instances of {@link ComponentParameterization} are immutable.
 *
 * @param <M> the actual {@link ParameterizedModel} sub-type of the component being built.
 *
 * @since 1.5
 */
public class ComponentParameterizationBuilder<M extends ParameterizedModel> implements ComponentParameterization.Builder<M> {

  private M model;

  private final Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters = new HashMap<>();
  private Optional<ComponentIdentifier> identifier = empty();

  public ComponentParameterization.Builder<M> withModel(M model) {
    this.model = model;

    return this;
  }

  @Override
  public Builder<M> withParameter(String paramGroupName, String paramName, Object paramValue) throws IllegalArgumentException {
    ParameterGroupModel paramGroup = model.getParameterGroupModels()
        .stream()
        .filter(pgm -> pgm.getName().equals(paramGroupName))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("ParameterGroup does not exist: " + paramGroupName));

    ParameterModel parameter = paramGroup.getParameter(paramName)
        .orElseThrow(() -> new IllegalArgumentException("Parameter does not exist in group '" + paramGroupName + "': "
            + paramName));

    parameters.put(new Pair<>(paramGroup, parameter), paramValue);

    return this;
  }

  @Override
  public Builder<M> withParameter(ParameterGroupModel paramGroup, ParameterModel paramModel, Object paramValue) {
    parameters.put(new Pair<>(paramGroup, paramModel), paramValue);
    return this;
  }

  @Override
  public Builder<M> withParameter(String paramName, Object paramValue) throws IllegalArgumentException {
    List<ParameterGroupModel> paramGroupsWithParamNamed = model.getParameterGroupModels()
        .stream()
        .filter(pgm -> pgm.getParameter(paramName).isPresent())
        .collect(toList());

    if (paramGroupsWithParamNamed.isEmpty()) {
      throw new IllegalArgumentException("Parameter does not exist in any group: " + paramName);
    } else if (paramGroupsWithParamNamed.size() > 1) {
      throw new IllegalArgumentException("Parameter exists in more than one group ("
          + paramGroupsWithParamNamed.stream().map(pgm -> pgm.getName()).collect(toList()) + "): " + paramName);
    }

    ParameterGroupModel paramGroup = paramGroupsWithParamNamed.get(0);
    ParameterModel paramModel = paramGroupsWithParamNamed.get(0).getParameter(paramName).get();
    return this.withParameter(paramGroup, paramModel, paramValue);
  }

  @Override
  public Builder<M> withComponentIdentifier(ComponentIdentifier identifier) {
    this.identifier = of(identifier);
    return this;
  }

  @Override
  public ComponentParameterization<M> build() {
    // TODO W-11214382 validate all required params are present
    // TODO W-11214382 set values for unset params with default values
    return new DefaultComponentParameterization<>(model, unmodifiableMap(parameters), identifier);
  }

  private static class DefaultComponentParameterization<M extends ParameterizedModel> implements ComponentParameterization<M> {

    private final M model;

    private final Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters;
    private final Map<Pair<String, String>, Object> parametersByNames;
    private final Optional<ComponentIdentifier> identifier;

    public DefaultComponentParameterization(M model, Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters,
                                            Optional<ComponentIdentifier> identifier) {
      this.model = model;
      this.parameters = parameters;
      this.identifier = identifier;

      parametersByNames = unmodifiableMap(parameters.entrySet().stream()
          .collect(toMap(e -> new Pair<>(e.getKey().getFirst().getName(), e.getKey().getSecond().getName()),
                         e -> e.getValue())));
    }

    @Override
    public M getModel() {
      return model;
    }

    @Override
    public Object getParameter(String paramGroupName, String paramName) {
      return parametersByNames.get(new Pair<>(paramGroupName, paramName));
    }

    @Override
    public Object getParameter(ParameterGroupModel paramGroup, ParameterModel param) {
      return parameters.get(new Pair<>(paramGroup, param));
    }

    @Override
    public Map<Pair<ParameterGroupModel, ParameterModel>, Object> getParameters() {
      return parameters;
    }

    @Override
    public void forEachParameter(ParameterAction action) {
      parameters.entrySet().forEach(e -> action.accept(e.getKey().getFirst(), e.getKey().getSecond(), e.getValue()));
    }

    @Override
    public Optional<ComponentIdentifier> getComponentIdentifier() {
      return identifier;
    }

  }
}
