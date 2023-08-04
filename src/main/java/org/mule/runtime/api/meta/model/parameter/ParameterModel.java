/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.api.meta.model.parameter;

import org.mule.api.annotation.NoImplement;
import org.mule.metadata.api.model.StringType;
import org.mule.runtime.api.meta.DescribedObject;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.api.meta.model.declaration.fluent.HasSemanticTerms;
import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.meta.Typed;
import org.mule.runtime.api.meta.model.ComponentModel;
import org.mule.runtime.api.meta.model.EnrichableModel;
import org.mule.runtime.api.meta.model.ParameterDslConfiguration;
import org.mule.runtime.api.meta.model.config.ConfigurationModel;
import org.mule.runtime.api.meta.model.deprecated.DeprecableModel;
import org.mule.runtime.api.meta.model.display.HasDisplayModel;
import org.mule.runtime.api.meta.model.display.HasLayoutModel;
import org.mule.runtime.api.meta.model.display.LayoutModel;
import org.mule.runtime.api.meta.model.stereotype.StereotypeModel;
import org.mule.runtime.api.meta.model.version.HasMinMuleVersion;
import org.mule.runtime.api.value.Value;

import java.util.List;
import java.util.Optional;

/**
 * A parameter of a {@link ComponentModel Component} or Configuration
 * <p>
 * A parameter provides a name, a type and a default value.
 * </p>
 * It can apply either to a {@link ConfigurationModel} or a {@link ComponentModel}
 *
 * @since 1.0.0
 */
@NoImplement
public interface ParameterModel
    extends NamedObject, DescribedObject, EnrichableModel, Typed, HasDisplayModel, HasLayoutModel, DeprecableModel,
    HasSemanticTerms, HasMinMuleVersion {

  /**
   * Whether or not this parameter is required. This method is exclusive with {@link #getDefaultValue()} in the sense that a
   * required parameter cannot have a default value. At the same time, if the parameter has a default value, then it makes no
   * sense to consider it as required
   *
   * @return a boolean value saying if this parameter is required or not
   */
  boolean isRequired();

  /**
   * Whether or not {@code this} {@link ParameterModel parameter} is bound to a {@link ParameterModel} of the same
   * {@link ParameterModel#getName() name} and {@link ParameterModel#getType() type} that exists in the {@link ConfigurationModel
   * config} associated to the {@link ComponentModel container} of {@code this} {@link ParameterModel parameter}. <br>
   * When {@code true}, the {@link ParameterModel parameter} will be injected with the same value of the bound
   * {@link ParameterModel parameter} of the {@link ConfigurationModel config} that's been associated to the execution.
   *
   * @return a boolean value saying if this parameter acts as an override for a parameter defined in a config.
   */
  boolean isOverrideFromConfig();

  /**
   * The level of support {@code this} parameter has for expressions
   *
   * @return a {@link ExpressionSupport}
   */
  ExpressionSupport getExpressionSupport();

  /**
   * The default value for this parameter. It might be an expression if {@link #getExpressionSupport()} returns
   * {@link ExpressionSupport#REQUIRED} or {@link ExpressionSupport#SUPPORTED}.
   * <p>
   * This method is exclusive with {@link #isRequired()}. Check that method's comments for more information on the semantics of
   * this two methods.
   *
   * @return the default value
   */
  Object getDefaultValue();

  /**
   * @return A {@link ParameterDslConfiguration} which describes the language which allows configuring this parameter
   */
  ParameterDslConfiguration getDslConfiguration();

  /**
   * @return this {@link ParameterModel}'s role
   */
  ParameterRole getRole();

  /**
   * {@inheritDoc} The value of {@link LayoutModel#getOrder()} is to be consider relative to the owning
   * {@link ParameterGroupModel}
   */
  @Override
  Optional<LayoutModel> getLayoutModel();

  /**
   * @return a {@link List} of {@link StereotypeModel}s that this {@link ParameterModel} should accept values of.
   */
  List<StereotypeModel> getAllowedStereotypes();

  /**
   * @return A {@link ValueProviderModel} to communicate if the this parameter model is capable to provide {@link Value values}.
   *         an {@link Optional#empty()} if there is no model associate to this parameter.
   */
  Optional<ValueProviderModel> getValueProviderModel();

  /**
   * @return A {@link List} of {@link FieldValueProviderModel}s if the fields of this parameter are capable of providing
   *         {@link Value values}
   *
   * @since 1.4
   */
  List<FieldValueProviderModel> getFieldValueProviderModels();

  /**
   * Whether or not this {@link ParameterModel} is declared as ID of the owning {@link ComponentModel}. Being a Component ID means
   * that the value associated to the annotated parameter can be used to reference the {@link ComponentModel component} in a mule
   * application uniquely across all the instances of the same {@link ComponentModel}. When used on a global element of the
   * application, then this Component ID serves as a global ID in the application.
   * <p>
   * An example of a {@link ComponentModel} ID is the {@code name} parameter of a {@code config} element.
   * <p>
   * Restrictions apply in order for a {@link ParameterModel} to be a {@link ComponentModel} ID:
   * <ul>
   * <li>Only <b>one</b> {@link ParameterModel parameter} can be component ID for any given {@link ComponentModel}</li>
   * <li>Only <b>required</b> {@link ParameterModel parameters} serve as Component ID</li>
   * <li>The parameter's type has to be {@link StringType String}</li>
   * <li>The parameter's expression support will be {@link ExpressionSupport#NOT_SUPPORTED}, so no dynamic values are allowed</li>
   * <li>{@link ParameterRole#CONTENT} qualifier is not allowed for a Component ID</li>
   * <li>{@code Text} qualifier is not allowed for a Component ID</li>
   * <li>{@code Query} qualifier is not allowed for a Component ID</li>
   * <li>Defaulting to a {@code ConfigOverride} is not allowed for a Component ID since it describes the ID of each individual
   * component and no common global value should be used as ID</li>
   * </ul>
   *
   * @return {@code true} if {@code this} {@link ParameterModel} is a {@link ComponentModel} ID.
   */
  boolean isComponentId();

}
