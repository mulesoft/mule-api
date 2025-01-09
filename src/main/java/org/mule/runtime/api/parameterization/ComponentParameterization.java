/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.api.parameterization;

import org.mule.api.annotation.Experimental;
import org.mule.api.annotation.NoImplement;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.meta.model.parameter.ParameterGroupModel;
import org.mule.runtime.api.meta.model.parameter.ParameterModel;
import org.mule.runtime.api.meta.model.parameter.ParameterizedModel;
import org.mule.runtime.api.util.Pair;
import org.mule.runtime.internal.parameterization.ComponentParameterizationBuilder;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a specific configuration for a concrete {@link ParameterizedModel}.
 * <p>
 * Instances are validated during creation, so no instances must contain invalid parameters according to the {@link #getModel()
 * model}.
 *
 * @param <M> the actual {@link ParameterizedModel} sub-type of the component .
 * 
 * @since 1.5
 */
@NoImplement
@Experimental
public interface ComponentParameterization<M extends ParameterizedModel> {

  /**
   * @return the actual model of the declared component.
   */
  M getModel();

  /**
   * Obtains the value of a parameter of the declared component.
   * 
   * @param paramGroupName the name of the parameter group
   * @param paramName      the name of the parameter within the group
   * @return the value of the parameter.
   */
  Object getParameter(String paramGroupName, String paramName);

  /**
   * Obtains the value of a parameter of the declared component.
   * 
   * @param paramGroup the model of the parameter group
   * @param param      the model of the parameter within the group
   * @return the value of the parameter.
   */
  Object getParameter(ParameterGroupModel paramGroup, ParameterModel param);

  /**
   * Returns all the parameters of the declared model that have values.
   * <p>
   * Parameters with a default value but no explicit value set are contained in the returned map.
   * 
   * @return the parameters as a map.
   */
  Map<Pair<ParameterGroupModel, ParameterModel>, Object> getParameters();

  /**
   * Iterates through the parameters that have values and calls the provided {@code action} on each one.
   * <p>
   * Parameters with a default value but no explicit value set have the action called for.
   * 
   * @param action a callback to be called for every parameter.
   */
  void forEachParameter(ParameterAction action);

  /**
   * Callback to be used with {@link ComponentParameterization#forEachParameter(ParameterAction)}.
   */
  public interface ParameterAction {

    void accept(ParameterGroupModel paramGroup, ParameterModel param, Object paramValue);
  }

  /**
   * @return {@link ComponentIdentifier} of the component this parameterizes.
   */
  Optional<ComponentIdentifier> getComponentIdentifier();

  /**
   * Builder that allows to create new {@link ComponentParameterization} instances.
   *
   * @param <M> the actual {@link ParameterizedModel} sub-type of the component being built.
   */
  public interface Builder<M extends ParameterizedModel> {

    /**
     * Performs validations on the parameters provided through {@link #withParameter(String, String, Object)}. If valid, returns a
     * {@link ComponentParameterization}, otherwise an {@link IllegalStateException} is thrown.
     * 
     * @return a valid {@link ComponentParameterization}.
     * @throws IllegalStateException if any provided parameter is invalid, indicating which parameter and the reason for it being
     *                               invalid.
     */
    ComponentParameterization<M> build() throws IllegalStateException;

    /**
     * Sets a parameter with a given value.
     * 
     * @param paramGroupName the name of the group containing the parameter to set.
     * @param paramName      the name of the parameter within the {@code paramGroupName} group to set.
     * @param paramValue     the value of the parameter to set
     * @return this builder
     * @throws IllegalArgumentException if the provided parameter group and name does not exist for the {@code model} or is not of
     *                                  a valid type.
     */
    Builder<M> withParameter(String paramGroupName, String paramName, Object paramValue) throws IllegalArgumentException;

    /**
     * Sets a parameter with a given value, automatically determining the group the parameter belongs to.
     * 
     * @param paramName  the name of the parameter within the {@code paramGroupName} group to set.
     * @param paramValue the value of the parameter to set
     * @return this builder
     * @throws IllegalArgumentException if the provided parameter name does not exist for the {@code model}, a parameter with the
     *                                  same name exists in more than on parameter group, or is not of a valid type.
     */
    Builder<M> withParameter(String paramName, Object paramValue) throws IllegalArgumentException;

    /**
     * Sets a parameter with the given ParameterGroupModel and ParameterModel
     *
     * @param paramGroup - the {@link ParameterGroupModel} of the Parameter
     * @param paramModel - the {@link ParameterModel} of the Parameter
     * @param paramValue - the value of the parameter to set.
     * @return this Builder.
     */
    Builder<M> withParameter(ParameterGroupModel paramGroup, ParameterModel paramModel, Object paramValue);

    /**
     * @param identifier - the {@link ComponentIdentifier} of the parameterized component.
     * @return this builder
     */
    Builder<M> withComponentIdentifier(ComponentIdentifier identifier);

  }

  /**
   * Provides a brand new builder instance for creating a {@link ComponentParameterization}.
   * 
   * @param <M>   the actual {@link ParameterizedModel} sub-type of the component.
   * @param model the actual model of the declared component.
   * @return a new builder instance.
   */
  public static <M extends ParameterizedModel> Builder<M> builder(M model) {
    return new ComponentParameterizationBuilder<M>()
        .withModel(model);
  }
}
