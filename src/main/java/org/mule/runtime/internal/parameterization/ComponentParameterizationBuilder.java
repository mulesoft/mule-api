/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.internal.parameterization;

import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import static com.github.benmanes.caffeine.cache.Caffeine.newBuilder;

import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.parameterization.ComponentParameterization;
import org.mule.runtime.api.parameterization.ComponentParameterization.Builder;
import org.mule.runtime.api.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.benmanes.caffeine.cache.LoadingCache;

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

  private static final LoadingCache<ParameterizedModel, ComponentCache> CACHE = newBuilder()
      .softValues()
      .build(ComponentCache::new);

  private M model;

  private final Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters = new HashMap<>();
  private final Map<Pair<String, String>, Object> parametersByNames = new HashMap<>();
  private Optional<ComponentIdentifier> identifier = empty();

  public ComponentParameterization.Builder<M> withModel(M model) {
    this.model = model;
    return this;
  }

  @Override
  public Builder<M> withParameter(String groupName, String paramName, Object value) throws IllegalArgumentException {
    parameters.put(CACHE.get(model).getPair(groupName, paramName), value);
    parametersByNames.put(new Pair<>(groupName, paramName), value);
    return this;
  }


  @Override
  public Builder<M> withParameter(ParameterGroupModel groupModel, ParameterModel paramModel, Object value) {
    parameters.put(new Pair<>(groupModel, paramModel), value);
    parametersByNames.put(new Pair<>(groupModel.getName(), paramModel.getName()), value);
    return this;
  }

  @Override
  public Builder<M> withParameter(String paramName, Object value) throws IllegalArgumentException {
    Pair<ParameterGroupModel, ParameterModel> pair = CACHE.get(model).getPair(paramName);
    return withParameter(pair.getFirst(), pair.getSecond(), value);
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
    return new DefaultComponentParameterization<>(model, unmodifiableMap(parameters), unmodifiableMap(parametersByNames),
                                                  identifier);
  }

  private static class DefaultComponentParameterization<M extends ParameterizedModel> implements ComponentParameterization<M> {

    private final M model;

    private final Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters;
    private final Map<Pair<String, String>, Object> parametersByNames;
    private final Optional<ComponentIdentifier> identifier;

    public DefaultComponentParameterization(M model,
                                            Map<Pair<ParameterGroupModel, ParameterModel>, Object> parameters,
                                            Map<Pair<String, String>, Object> parametersByNames,
                                            Optional<ComponentIdentifier> identifier) {
      this.model = model;
      this.parameters = parameters;
      this.parametersByNames = parametersByNames;
      this.identifier = identifier;
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

  private static class ComponentCache {

    private final ParameterizedModel model;
    private final LoadingCache<String, ParameterGroupModel> groups;
    private final LoadingCache<Pair<String, String>, Pair<ParameterGroupModel, ParameterModel>> params;
    private final LoadingCache<String, Pair<ParameterGroupModel, ParameterModel>> defaultParams;

    public ComponentCache(ParameterizedModel model) {
      this.model = model;
      groups = newBuilder().build(this::getGroupByName);
      params = newBuilder().build(this::toModelPair);
      defaultParams = newBuilder().build(this::findDefaultParamByName);
    }

    public Pair<ParameterGroupModel, ParameterModel> getPair(String groupName, String paramName) {
      return params.get(new Pair<>(groupName, paramName));
    }

    public Pair<ParameterGroupModel, ParameterModel> getPair(String paramName) {
      return defaultParams.get(paramName);
    }

    private ParameterGroupModel getGroupByName(String paramGroupName) {
      return model.getParameterGroupModels()
          .stream()
          .filter(pgm -> pgm.getName().equals(paramGroupName))
          .findAny()
          .orElseThrow(() -> new IllegalArgumentException("ParameterGroup does not exist: " + paramGroupName));
    }

    private Pair<ParameterGroupModel, ParameterModel> findDefaultParamByName(String paramName) {
      ParameterGroupModel groupModel = null;
      ParameterModel parameterModel = null;

      for (ParameterGroupModel searchModel : model.getParameterGroupModels()) {
        Optional<ParameterModel> parameter = searchModel.getParameter(paramName);
        if (parameter.isPresent()) {
          if (groupModel == null) {
            groupModel = searchModel;
          } else {
            throw new IllegalArgumentException(
                                               "Parameter '" + paramName + "' exists in more than one group for component '"
                                                   + model.getName() + "'");
          }
          if (parameterModel == null) {
            parameterModel = parameter.get();
          }
        }
      }

      if (parameterModel == null) {
        throw new IllegalArgumentException("Parameter does not exist in any group: " + paramName);
      }
      return new Pair<>(groupModel, parameterModel);
    }

    private Pair<ParameterGroupModel, ParameterModel> toModelPair(Pair<String, String> pair) {
      ParameterGroupModel paramGroup = groups.get(pair.getFirst());
      String paramName = pair.getSecond();

      return paramGroup.getParameter(paramName)
          .map(p -> new Pair<>(paramGroup, p))
          .orElseThrow(() -> new IllegalArgumentException("Parameter does not exist in group '" + paramGroup.getName() + "': "
              + paramName));
    }
  }
}
